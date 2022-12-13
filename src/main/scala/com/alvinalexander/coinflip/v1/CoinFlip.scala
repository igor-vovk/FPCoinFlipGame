package com.alvinalexander.coinflip.v1

import CoinFlipUtils._
import scala.annotation.tailrec
import scala.util.Random
import cats.effect.{ExitCode, IO, IOApp}

case class GameState(numFlips: Int, numCorrect: Int)

object CoinFlip extends IOApp {

    def printableFlipResult(flip: String): String = flip match {
        case "H" => "Heads"
        case "T" => "Tails"
    }

    def run (args: List[String]): IO[ExitCode] = {
        val r = Random
        val s = GameState(0, 0)
        mainLoop(s, r).as(ExitCode.Success)
    }

    def mainLoop(gameState: GameState, random: Random): IO[Unit] = {

        for {
            _ <- showPrompt()
            userInput <- getUserInput()
            _ <- userInput match {
                case "H" | "T" => for {
                    coinTossResult <- tossCoin(random)
                    newNumFlips = gameState.numFlips + 1
                    
                    newGameState = 
                        if (userInput == coinTossResult) {
                            val newNumCorrect = gameState.numCorrect + 1
                            
                            gameState.copy(
                                numFlips = newNumFlips, 
                                numCorrect = newNumCorrect
                            )
                        } else {
                            gameState.copy(
                                numFlips = newNumFlips
                            )
                        }

                    _ <- printGameState(printableFlipResult(coinTossResult), newGameState)
                    _ <- mainLoop(newGameState, random)
                } yield ()

                case _   => for {
                    _ <- printGameOver()
                    _ <- printGameState(gameState)
                    // return out of the recursion here
                } yield ()
            }
        } yield ()
    }
}

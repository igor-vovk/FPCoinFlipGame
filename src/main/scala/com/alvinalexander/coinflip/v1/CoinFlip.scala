package com.alvinalexander.coinflip.v1

import CoinFlipUtils._
import scala.annotation.tailrec
import cats.effect.{ExitCode, IO, IOApp}

case class GameState(numFlips: Int, numCorrect: Int)

object CoinFlip extends IOApp {

  def printableFlipResult(flip: String): String = flip match
    case "H" => "Heads"
    case "T" => "Tails"

  def run (args: List[String]): IO[ExitCode] = 
    val s = GameState(0, 0)
    mainLoop(s).as(ExitCode.Success)

  def mainLoop(gameState: GameState): IO[Unit] = 

    for 
      _         <- showPrompt()
      userInput <- getUserInput()
      _         <- userInput match 
        case "H" | "T" => 
          for 
            coinTossResult <- tossCoin()
            
            newGameState = GameState(
              gameState.numFlips + 1,
              gameState.numCorrect + { if userInput == coinTossResult then 1 else 0 }
            )

            _ <- printGameState(printableFlipResult(coinTossResult), newGameState)
            _ <- mainLoop(newGameState)
          yield ()

        case _   =>
          for
            _ <- printGameOver()
            _ <- printGameState(gameState)
          yield ()
      
    yield ()
  
}

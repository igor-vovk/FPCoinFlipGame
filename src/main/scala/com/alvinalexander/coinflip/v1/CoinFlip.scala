package com.alvinalexander.coinflip.v1

import CoinFlipUtils._
import scala.annotation.tailrec
import cats.effect.{ExitCode, IO, IOApp}

case class GameState(numFlips: Int, numCorrect: Int)

object CoinFlip extends IOApp {

  def printableFlipResult(flip: String): String = flip match
    case "H" => "Heads"
    case "T" => "Tails"

<<<<<<< HEAD
  def run (args: List[String]): IO[ExitCode] = 
    val s = GameState(0, 0)
    mainLoop(s).as(ExitCode.Success)
=======
    def run (args: List[String]): IO[ExitCode] = {
        val r = Random
        val s = GameState(0, 0)
        mainLoop(s, r).as(ExitCode.Success)
    }
>>>>>>> dffd37a54bca01ceeb35d6017a4dd8bd8e2f79a4

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

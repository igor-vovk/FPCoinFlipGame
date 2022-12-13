package com.alvinalexander.coinflip.v1

import scala.util.Random
import scala.io.StdIn.readLine
import cats.effect.IO

object CoinFlipUtils {

    def showPrompt(): IO[Unit] = IO {
        print("\n(h)eads, (t)ails, or (q)uit: ") 
    }

    def getUserInput(): IO[String] = IO { 
        readLine.trim.toUpperCase
    }

    def printGameState(printableFlipResult: String, gameState: GameState): IO[Unit] = IO {
        print(s"Flip was $printableFlipResult. ")
        println(s"#Flips: ${gameState.numFlips}, #Correct: ${gameState.numCorrect}")
    }

    def printGameState(gameState: GameState): IO[Unit] = IO {
        println(s"#Flips: ${gameState.numFlips}, #Correct: ${gameState.numCorrect}")
    }

    def printGameOver(): IO[Unit] = IO {
        println("\n=== GAME OVER ===")
    }

    // returns "H" for heads, "T" for tails
    def tossCoin(r: Random): IO[String] = IO {
        val i = r.nextInt(2)
        
        i match {
            case 0 => "H"
            case 1 => "T"
        }
    }

}

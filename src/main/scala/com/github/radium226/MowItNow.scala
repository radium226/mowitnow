package com.github.radium226

import mowitnow._
import mowitnow.Readers._
import io._

import scala.util.{Failure, Success, Try}

object MowItNow {

  def main(arguments: Array[String]): Unit = {
    val lines =
      """
        |5 5
        |1 2 N
        |GAGAGAGAA
        |3 3 E
        |AADAADADDA
      """.stripMargin.trim().split("\n").map(_.trim).toSeq

    println(lines)

    val finalStates = IO.read[(Size, Seq[Program])](lines).flatMap { case (size, programs) =>
      implicit val s = size
      programs.foldLeft[Try[Seq[State]]](Success(Seq())) { (tryFinalStates, program) =>
        val Program(initialState, actions) = program
        for {
          finalStates <- tryFinalStates
          finalState <- Mower(actions).mow(initialState)
        } yield finalStates :+ finalState
      }
    }

    print(finalStates)

  }

}

package com.github.radium226.mowitnow

import com.github.radium226.mowitnow.model._
import com.github.radium226.mowitnow.model.parsing.{ActionsParser, SizeParser, StateParser}

import scala.util.{Failure, Success, Try}

object Mow {

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

    val finalStates: Try[Seq[Try[State]]] = lines match {
      case Seq(sizeLine, otherLines @ _ *) => Success({
        otherLines.sliding(2, 2).toSeq.map({
          case Seq(initialStateLine, actionsLine) => {
            println(s"initialStateLine=$initialStateLine")
            for {
              size <- SizeParser.parse(sizeLine)
              initialState <- StateParser.parse(initialStateLine)
              actions <- ActionsParser.parse(actionsLine)
              finalState <- Mower(actions).mow(initialState)(size)
            } yield finalState
          }
          case _ => Failure(new Exception("Error while parsing! "))
        })
      })
      case _ => Failure(new Exception("Incoherent line count! "))
    }

    finalStates match {
      case Success(maybeFinalStates) => maybeFinalStates.foreach({
        case Success(finalState) => println(s"finalState=$finalState")
      })
    }

  }

}

package com.github.radium226

import io._

import mowitnow._
import mowitnow.io._

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

    val finalStates = for {
      sizeAndPrograms <- IO.read[(Size, Seq[Program])](lines)
      finalStates <- Mower.mow(sizeAndPrograms)
    } yield finalStates

    print(finalStates)

  }

}

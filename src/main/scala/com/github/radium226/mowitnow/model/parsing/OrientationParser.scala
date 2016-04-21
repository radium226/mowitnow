package com.github.radium226.mowitnow.model.parsing

import com.github.radium226.mowitnow.model._

import scala.util.{ Try, Success, Failure }

object OrientationParser extends Parser[Orientation] {

  val OrientationByLetter = Map(
    "N" -> Orientation.North(),
    "S" -> Orientation.South(),
    "E" -> Orientation.East(),
    "W" -> Orientation.West()
  )

  def parse(letter: String): Try[Orientation] = {
    OrientationByLetter.get(letter).fold[Try[Orientation]](Failure(new Exception("Unable to parse orientation")))(Success(_))
  }

}

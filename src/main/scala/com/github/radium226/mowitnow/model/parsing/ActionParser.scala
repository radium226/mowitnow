package com.github.radium226.mowitnow.model.parsing

import java.text.ParseException

import com.github.radium226.mowitnow.model.Action.{TurnLeft, TurnRight, MoveForward}
import com.github.radium226.mowitnow.model._

import scala.util.{Failure, Success, Try}

object ActionParser extends Parser[Action] {

  val ActionByLetter = Map(
    "A" -> MoveForward(),
    "D" -> TurnRight(),
    "G" -> TurnLeft()
  )

  def parse(letter: String): Try[Action] = {
    ActionByLetter.get(letter).fold[Try[Action]](Failure(new Exception("Unable to parse action")))(Success(_))
  }

}

package com.github.radium226.mowitnow.model.parsing

import java.text.ParseException

import com.github.radium226.mowitnow.model._

import scala.util.{ Try, Success, Failure }

object ActionsParser extends Parser[Seq[Action]] {

  def parse(letters: String): Try[Seq[Action]] = {
    val actions = letters.sliding(1).toSeq.map(ActionParser.parse(_)).collect {
      case Success(action) => action
    }
    Success(actions)
  }

}

package com.github.radium226.mowitnow.model.parsing

import com.github.radium226.mowitnow.model._
import scala.util.{ Try, Success, Failure }

object SizeParser extends Parser[Size] {

  val SizePattern = "([0-9]+) ([0-9]+)".r

  def parse(text: String): Try[Size] = {
    text match {
      case SizePattern(width, height) => Success(Size(width.toInt, height.toInt))
      case _ => Failure(new Exception("Unable to parse size"))
    }
  }

}

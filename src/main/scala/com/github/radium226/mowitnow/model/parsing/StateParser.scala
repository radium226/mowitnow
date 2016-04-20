package com.github.radium226.mowitnow.model.parsing

import com.github.radium226.mowitnow.model.parsing._
import com.github.radium226.mowitnow.model._

import scala.util.{ Try, Success, Failure }

object StateParser extends Parser[State] {

  val StatePattern = "^([0-9]+) ([0-9]+) ([A-Z])$".r

  def parse(text: String): Try[State] = text match {
      case StatePattern(x, y, orientation) => OrientationParser.parse(orientation).map(State(Position(x.toInt, y.toInt), _))
      case _ => Failure(new Exception("Unable to parse state"))
  }

}

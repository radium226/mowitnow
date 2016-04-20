package com.github.radium226.mowitnow.model

sealed trait Direction {

  val name: String

}

case class LeftDirection() extends Direction {
  val name = "G"
}

case class RightDirection() extends Direction {
  val name = "D"
}

case class UnknownDirection(name: String) extends Direction

object Direction {

  val all = Seq(LeftDirection(), RightDirection())

  def named(name: String): Direction = all.find(_.name == name).getOrElse(UnknownDirection(name))

}
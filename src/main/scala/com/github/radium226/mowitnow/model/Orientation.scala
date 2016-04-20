package com.github.radium226.mowitnow.model

sealed trait Orientation {

  val name: String

  val opposite: Orientation

  val right: Orientation

  def left(): Orientation = opposite.right

}

case class NorthOrientation() extends Orientation {

  val name = "N"

  lazy val opposite = SouthOrientation()

  lazy val right = EastOrientation()
}

case class SouthOrientation() extends Orientation {

  val name = "S"

  lazy val opposite = NorthOrientation()

  lazy val right = WestOrientation()

}

case class EastOrientation() extends Orientation {

  val name = "E"

  lazy val opposite = WestOrientation()

  lazy val right = SouthOrientation()
}

case class WestOrientation() extends Orientation {

  val name = "W"

  lazy val opposite = EastOrientation()

  lazy val right = NorthOrientation()

}

case class UnknownOrientation(name: String, opposite: Orientation, right: Orientation) extends Orientation

object Orientation {

  val all = Seq(NorthOrientation(), SouthOrientation(), EastOrientation(), WestOrientation())

  def named(name: String): Orientation = all.find(_.name == name).getOrElse(UnknownOrientation(name, null, null))

}
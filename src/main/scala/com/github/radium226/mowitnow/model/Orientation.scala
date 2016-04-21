package com.github.radium226.mowitnow.model

sealed trait Orientation {

  val opposite: Orientation

  val right: Orientation

  def left(): Orientation = opposite.right

}

object Orientation {

  case class North() extends Orientation {

    lazy val opposite = South()

    lazy val right = East()
  }

  case class South() extends Orientation {

    lazy val opposite = North()

    lazy val right = West()

  }

  case class East() extends Orientation {

    lazy val opposite = West()

    lazy val right = South()
  }

  case class West() extends Orientation {

    lazy val opposite = East()

    lazy val right = North()

  }

}
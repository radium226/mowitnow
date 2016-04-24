package com.github.radium226.mowitnow

/**
  * It models an orientation
  */
sealed trait Orientation {

  /**
    * The opposite of the current orientation
    */
  val opposite: Orientation

  /**
    * The orientation on the right of the current orientation
    */
  val right: Orientation

  /**
    * The orientation on the left of the current orientation
    * @return The orientation on the left
    */
  def left(): Orientation = opposite.right

}

object Orientation {

  /**
    * The North orientation
    */
  case class North() extends Orientation {

    lazy val opposite = South()

    lazy val right = East()
  }

  /**
    * The South orientation
    */
  case class South() extends Orientation {

    lazy val opposite = North()

    lazy val right = West()

  }

  /**
    * The East orientation
    */
  case class East() extends Orientation {

    lazy val opposite = West()

    lazy val right = South()
  }

  /**
    * The West orientation
    */
  case class West() extends Orientation {

    lazy val opposite = East()

    lazy val right = North()

  }

}
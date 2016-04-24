package com.github.radium226.mowitnow

/**
  * It models a direction where to turn
  */
sealed trait Direction {

}

object Direction {

  /**
    * The left direction
    */
  case class Left() extends Direction

  /**
    * The right direction
    */
  case class Right() extends Direction

}
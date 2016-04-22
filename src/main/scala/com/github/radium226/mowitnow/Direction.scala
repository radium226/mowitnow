package com.github.radium226.mowitnow

sealed trait Direction {

}

object Direction {

  case class Left() extends Direction

  case class Right() extends Direction

}
package com.github.radium226.mowitnow

/**
  * Created by adrien on 4/20/16.
  */
package object model {

  case class Position(x: Int, y: Int)

  case class Size(width: Int, height: Int)

  case class State(position: Position, orientation: Orientation)

}

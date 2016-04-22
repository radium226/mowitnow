package com.github.radium226

package object mowitnow {

  case class Position(x: Int, y: Int)

  case class Size(width: Int, height: Int)

  case class State(position: Position, orientation: Orientation)

  case class Program(initialState: State, actions: Seq[Action])

}

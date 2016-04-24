package com.github.radium226

package object mowitnow {

  /**
    * It models position
    * @param x The X-axis coordinate
    * @param y The Y-axis coordinate
    */
  case class Position(x: Int, y: Int)

  /**
    * It models the size of the lawn
    * @param width The width of the lawn boundaries
    * @param height The height of the lawn boundaries
    */
  case class Size(width: Int, height: Int)

  /**
    * The state of the mower
    * @param position The position of the mower
    * @param orientation The orientation of the mower
    */
  case class State(position: Position, orientation: Orientation)

  /**
    * The program the mower should follow
    * @param initialState The initial state
    * @param actions Multiple actions
    */
  case class Program(initialState: State, actions: Seq[Action])

}

package com.github.radium226.mowitnow.model

import Math.{ min, max }
import scala.util.{ Try, Success, Failure }

sealed trait Action {

  def apply(state: State)(size: Size): Try[State]

}

object Action {

  case class MoveForward() extends Action {

    def apply(state: State)(size: Size): Try[State] = {
      val Size(width, height) = size
      val State(Position(x, y), orientation) = state
      val position = orientation match {
        case Orientation.North() => Position(x, min(y + 1, height))
        case Orientation.East() => Position(min(x + 1, width), y)
        case Orientation.West() => Position(max(x - 1, 0), y)
        case Orientation.South() => Position(x, max(y - 1, 0))
        case _ => Position(x, y)
      }
      Success(State(position, orientation))
    }

  }

  case class TurnLeft() extends Action {

    def apply(state: State)(size: Size): Try[State] = Success(State(state.position, state.orientation.left))

  }

  case class TurnRight() extends Action {

    def apply(state: State)(size: Size): Try[State] = Success(State(state.position, state.orientation.right))

  }

  case class CheckInitialState() extends Action {

    def apply(state: State)(size: Size): Try[State] = (state, size) match {
      case (State(Position(x, y), _), Size(width, height)) if (0.until(width).contains(x) && 0.until(height).contains(y)) => Success(state)
      case _ => Failure(new Exception("Initial state seems to be wrong! "))
    }

  }
}


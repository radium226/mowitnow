package com.github.radium226.mowitnow

import java.lang.Math.{max, min}

import scala.util.{Failure, Success, Try}

/**
  * It models every action the mower can do
  */
sealed trait Action {

  /**
    * Given a state, apply the current action and return the next state
    * @param state The current state
    * @param size The size of lawn
    * @return The next state
    */
  def apply(state: State)(size: Size): Try[State]

}

object Action {

  /**
    * Make the mower move forward (the state does not change if the mower is at the edge of the lawn)
    */
  case class MoveForward() extends Action {

    def apply(state: State)(size: Size): Try[State] = {
      val Size(width, height) = size
      val State(Position(x, y), orientation) = state
      val position = orientation match {
        case Orientation.North() => Position(x, min(y + 1, height - 1))
        case Orientation.East() => Position(min(x + 1, width - 1), y)
        case Orientation.West() => Position(max(x - 1, 0), y)
        case Orientation.South() => Position(x, max(y - 1, 0))
      }
      Success(State(position, orientation))
    }

  }

  /**
    * Turn the mower to the left
    */
  case class TurnLeft() extends Action {

    def apply(state: State)(size: Size): Try[State] = Success(State(state.position, state.orientation.left))

  }

  /**
    * Turn the mower to the right
    */
  case class TurnRight() extends Action {

    def apply(state: State)(size: Size): Try[State] = Success(State(state.position, state.orientation.right))

  }

  /**
    * A special action used to check if the state is consistent (the position should be inside the lawn boudaries)
    */
  case class CheckState() extends Action {

    def apply(state: State)(size: Size): Try[State] = (state, size) match {
      case (State(Position(x, y), _), Size(width, height)) if (0.until(width).contains(x) && 0.until(height).contains(y)) => Success(state)
      case _ => Failure(new Exception("Initial state seems to be wrong"))
    }

  }

  /**
    * A special action used to ensure that the size of the lawn is consistent
    */
  case class CheckSize() extends Action {

    def apply(state: State)(size: Size): Try[State]  = size match {
      case Size(height, width) if (height > 0 && width > 0) => Success(state)
      case _ => Failure(new Exception("The lawn area size seems to be wrong"))
    }

  }
}


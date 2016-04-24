package com.github.radium226.mowitnow

import scala.util.{Success, Try}

/**
  * The purpose of this class is to mow.
  * @param actions
  */
class Mower(actions: Seq[Action]) {

  /**
    * Compute the final state using the actions provided in the constructor
    * @param initialState The initial state
    * @param size The size of the lawn
    * @return The final state
    */
  def mow(initialState: State)(implicit size: Size): Try[State] = {
    (Seq(Action.CheckSize(), Action.CheckState()) ++: actions).foldLeft(Try(initialState))({
      case (Success(state), action) => action(state)(size)
      case (failure, _) => failure
    })
  }

}

/**
  * Using this companion object is shortcut to mow with multiple mowers
  */
object Mower {

  /**
    * Compute the final states using programs which are composed by an initial state and multiple actions
    * @param sizeAndPrograms A tuple composed of the size of the lawn and the programs
    * @return The final states
    */
  def mow(sizeAndPrograms: (Size, Seq[Program])): Try[Seq[State]] = sizeAndPrograms match { case (size, programs) =>
    programs.foldLeft[Try[Seq[State]]](Success(Seq())) { (tryFinalStates, program) =>
      val Program(initialState, actions) = program
      for {
        finalStates <- tryFinalStates
        finalState <- new Mower(actions).mow(initialState)(size)
      } yield finalStates :+ finalState
    }
  }

}

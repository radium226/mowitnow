package com.github.radium226.mowitnow

import scala.util.{Success, Try}

class Mower(actions: Seq[Action]) {

  def mow(initialState: State)(implicit size: Size): Try[State] = {
    (Seq(Action.CheckSize(), Action.CheckState()) ++: actions).foldLeft(Try(initialState))({
      case (Success(state), action) => action(state)(size)
      case (failure, _) => failure
    })
  }

}

object Mower {

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

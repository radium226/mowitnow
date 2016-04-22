package com.github.radium226.mowitnow

import scala.util.{Success, Try}

case class Mower(actions: Seq[Action]) {

  def mow(initialState: State)(implicit size: Size): Try[State] = {
    (Seq(Action.CheckSize(), Action.CheckState()) ++: actions).foldLeft(Try(initialState))({
      case (Success(state), action) => action(state)(size)
      case (failure, _) => failure
    })
  }

}

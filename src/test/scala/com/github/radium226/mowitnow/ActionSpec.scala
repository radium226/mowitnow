package com.github.radium226.mowitnow

import com.github.radium226.mowitnow.model._
import scala.util.{ Success, Failure, Try }

class ActionSpec extends BaseSpec {

  feature("The mower can move forward") {

    scenario("The mower should stay in the lawn") {
      Given("an initial state in the edge of the lawn area")
      val initialState = State(Position(1, 1), Orientation.North())
      val size = Size(1, 1)

      When("the mower mow")
      val actions = Seq(Action.MoveForward())
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should succeed")
      finalState shouldBe a [Success[State]]
      And("be equal to the initial state")
      initialState shouldEqual finalState.get
    }

  }

}

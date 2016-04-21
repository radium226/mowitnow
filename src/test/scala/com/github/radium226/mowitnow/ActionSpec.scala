package com.github.radium226.mowitnow

import com.github.radium226.mowitnow.model._
import scala.util.{ Success, Failure, Try }

class ActionSpec extends BaseSpec {

  feature("The mower can move forward") {

    scenario("The mower should stay in the lawn") {
      Given("an initial state in the edge of the lawn area")
      val initialState = State(Position(0, 0), Orientation.North())
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

  feature("The initial state of the mower should be consitent") {
    scenario("The mower can't have an initial state outside of the lawn") {
      Given("an initial state outside of the lawn area")
      val initialState = State(Position(1, 1), Orientation.North())
      val size = Size(1, 1)

      When("the mower mow")
      val actions = Seq(Action.MoveForward())
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should fail")
      finalState shouldBe a [Failure[State]]
    }
  }

}

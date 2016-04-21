package com.github.radium226.mowitnow

import com.github.radium226.mowitnow.model._
import scala.util.{ Success, Failure, Try }

class ActionSpec extends BaseSpec {

  feature("The mower can move forward and turn") {

    scenario("The mower should stay in the lawn") {
      Given("an initial state in the edge of the lawn area")
      val initialState = State(Position(0, 0), Orientation.North())
      val size = Size(1, 1)

      When("the mower mow")
      val actions = Seq(Action.MoveForward())
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should succeed")
      finalState shouldBe a [Success[_]]

      And("be equal to the initial state")
      initialState shouldEqual finalState.get
    }

    scenario("The mower be in the same state if it does a complete clockwise turn") {
      Given("an initial state")
      val initialState = State(Position(0, 0), Orientation.North())
      val size = Size(1, 1)

      When("the mower mow by turining left 4 times")
      val actions = (1 to 4).map(_ => Action.TurnLeft())
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should succeed")
      finalState shouldBe a [Success[_]]

      And("be equal to the initial state")
      initialState shouldEqual finalState.get
    }

    scenario("The mower be in the same state if it does a complete counter-clockwise turn") {
      Given("an initial state")
      val initialState = State(Position(0, 0), Orientation.North())
      val size = Size(1, 1)

      When("the mower mow by turning right 4 times")
      val actions = (1 to 4).map(_ => Action.TurnRight())
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should succeed")
      finalState shouldBe a [Success[_]]

      And("be equal to the initial state")
      initialState shouldEqual finalState.get
    }

    scenario("The mower move forward and turn left successively in order to go back were it started") {
      Given("an initial state and a large lawn")
      val initialState = State(Position(10, 10), Orientation.North())
      val size = Size(100, 100)

      When("the action are moving forward and turning left successively")
      val actions = (1 to 4).map(_ => Seq(Action.MoveForward(), Action.TurnLeft())).flatten
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should succeed")
      finalState shouldBe a [Success[_]]

      And("be equal to the initial state")
      initialState shouldEqual finalState.get
    }

  }

  feature("The mower should be consitent") {
    scenario("The mower can't have an initial state outside of the lawn") {
      Given("an initial state outside of the lawn area")
      val initialState = State(Position(1, 1), Orientation.North())
      val size = Size(1, 1)

      When("the mower does nothing")
      val actions = Seq()
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should fail")
      finalState shouldBe a [Failure[_]]
    }

    scenario("The size of the lawn should be consistent") {
      Given("an initial state outside of the lawn area")
      val initialState = State(Position(1, 1), Orientation.North())
      val size = Size(0, -1)

      When("the mower does nothing")
      val actions = Seq()
      val finalState = Mower(actions).mow(initialState)(size)

      Then("the final state should fail")
      finalState shouldBe a [Failure[_]]
    }
  }

}

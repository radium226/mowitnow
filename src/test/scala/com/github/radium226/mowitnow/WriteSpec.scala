package com.github.radium226.mowitnow

import com.github.radium226.io._

import Action._
import Orientation._
import io._

import scala.util.Success

class WriteSpec extends BaseSpec {

  feature("All the classes can be wrotten") {

    scenario("Size and programs can be written") {
      Given("a size and multiple programs")
      val sizeAndPrograms = (Size(2, 2), Seq(Program(State(Position(1, 1), North()), Seq(MoveForward(), TurnLeft(), TurnRight()))))

      When("They are written")
      val lines = IO.write[(Size, Seq[Program])](sizeAndPrograms)

      Then("lines should be consistent")
      lines shouldEqual Success(fromSeq(Seq(
        "1 1",
        "1 1 N",
        "AGD"
      )))
    }

  }

  feature("Program can be written") {
    scenario("Program shoud be successfully written if lines are consistent") {
      Given("a program")
      val program = Program(State(Position(1, 1), North()), Seq(MoveForward(), TurnLeft()))

      When("it's written")
      val lines = IO.write(program)

      Then("lines should be consistent")
      lines shouldEqual Success(fromSeq(Seq("1 1 N", "AG")))
    }
  }

  feature("Actions can be written") {
    scenario("Actions should be successfully written") {
      Given("multiple actions")
      val actions = Seq(MoveForward(), TurnRight())

      When("they are written")
      val lines = IO.write[Seq[Action]](actions)

      Then("lines should be consistent")
      lines shouldEqual Success(fromSeq(Seq("AD")))
    }
  }

}

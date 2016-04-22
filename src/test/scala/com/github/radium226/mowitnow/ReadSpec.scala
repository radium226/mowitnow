package com.github.radium226.mowitnow

import com.github.radium226.io._
import Readers._
import Action._
import Orientation._

import scala.util.{Failure, Success}

class ReadSpec extends BaseSpec {

  feature("The lawn area size and the mower programs can be parsed") {
    scenario("A size can be read from a line") {
      Given("a line describing the top right corner")
      val line: String = "2 3"

      When("the line is read")
      val size = IO.read[Size](line)

      Then("the size be consistent with the line")
      size shouldEqual Success(Size(3, 4))

    }

    scenario("Reading a corrupted line sould fail") {
      Given("a corrupted line")
      val line = "La tête à Toto"

      When("the line is read")
      val size = IO.read[Size](line)

      Then("the size sould be a failure")
      size shouldBe a [Failure[_]]
    }
  }

  scenario("A program can be read from multiple lines") {
    Given("some valid lines")
    val lines =
      """1 2 N
        |AGD
      """.stripMargin.trim.split("\n")

    When("the lines are read")
    val program = IO.read[Program](lines)

    Then("the program should be a success corresponding to the lines")
    program shouldEqual Success(Program(State(Position(1, 2), North()), Seq(MoveForward(), TurnLeft(), TurnRight())))
  }

}

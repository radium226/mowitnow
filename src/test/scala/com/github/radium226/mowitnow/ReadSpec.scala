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
      size shouldBe a[Failure[_]]
    }
  }

  feature("All case classes can be read from text") {
    scenario("Size and programs can be read") {
      Given("some lines")
      val lines =
        """1 1
          |1 2 N
          |AGD
          |3 4 S
          |A
        """.stripMargin.trim.split("\n")

      When("the lines are read")
      val sizeAndPrograms = IO.read[(Size, Seq[Program])](lines)

      Then("it should be a success and the size and the programs should be consistent with the given lines")
      val size = Size(2, 2)
      val firstProgram = Program(State(Position(1, 2), North()), Seq(MoveForward(), TurnLeft(), TurnRight()))
      val secondProgram = Program(State(Position(3, 4), South()), Seq(MoveForward()))
      sizeAndPrograms shouldEqual Success((size, Seq(firstProgram, secondProgram)))
    }

    scenario("Everything should fail even if some lines are valid in text") {
      Given("some lines")
      val lines =
        """1 2 N
          |AGD
          |3 4 S
          |$
        """.stripMargin.trim.split("\n")

      When("the lines are read")
      val sizeAndPrograms = IO.read[(Size, Seq[Program])](lines)

      Then("it should be a failure")
      sizeAndPrograms shouldBe a[Failure[_]]
    }
  }

}

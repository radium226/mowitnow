package com.github.radium226.mowitnow

import scala.util.Success

import com.github.radium226.io._
import io._

class XebiaSpec extends BaseSpec {

  feature("Xebia's example should work") {
    scenario("By reading Xebia's example, it should produce what Xebia's expecting") {
      Given("Xebia's input")
      val inputLines =
        """5 5
          |1 2 N
          |GAGAGAGAA
          |3 3 E
          |AADAADADDA
        """.stripMargin

      When("The mower mow")
      val tryOutputLines = for {
        sizeAndPrograms <- IO.read[(Size, Seq[Program])](inputLines)
        finalStates <- Mower.mow(sizeAndPrograms)
        outputLines <- IO.write(finalStates)
      } yield outputLines

      Then("The output should be the same as Xebia's")
      tryOutputLines shouldEqual Success[Output](
        """1 3 N
          |5 1 E
        """.stripMargin.trim
      )
    }
  }

}

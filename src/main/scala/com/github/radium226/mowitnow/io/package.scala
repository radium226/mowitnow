package com.github.radium226.mowitnow

import com.github.radium226.util.Implicits._

import com.github.radium226.io.{IO, MappingBasedIO, SingleLineIO}
import com.github.radium226.mowitnow.Action.{MoveForward, TurnLeft, TurnRight}

import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

import scala.reflect.runtime.universe._

/**
  * Created by adrien on 4/22/16.
  */
package object io {

  implicit val actionIO: IO[Action] = new MappingBasedIO[Action] {

    override val tt: TypeTag[Action]  = typeTag[Action]

    val Mapping = Map(
      'A' -> MoveForward(),
      'D' -> TurnRight(),
      'G' -> TurnLeft()
    )

  }

  implicit val actionsIO: IO[Seq[Action]] = new SingleLineIO[Seq[Action]] {

    override val tt: TypeTag[Seq[Action]]  = typeTag[Seq[Action]]

    override def readLine(line: String): Try[Seq[Action]] = {
      line.sliding(1).map { IO.read[Action](_) }.foldLeft[Try[Seq[Action]]](Success(Seq())) { (tryActions, tryAction) =>
        for {
          actions <- tryActions
          action <- tryAction
        } yield actions :+ action
      }
    }

    override def writeLine(actions: Seq[Action]): Try[String] = {
      actions.map(IO.write(_)).foldLeft[Try[String]](Success("")) { (tryLine, tryChar) =>
        for {
          line <- tryLine
          seq <- tryChar
          char <- seq.lines.headOption.toTry(new Exception("Wrong state, here... "))
        } yield line + char
      }
    }

  }

  implicit val orientationIO: IO[Orientation] = new MappingBasedIO[Orientation] {

    override val tt: TypeTag[Orientation]  = typeTag[Orientation]

    override val Mapping = Map(
      'N' -> Orientation.North(),
      'S' -> Orientation.South(),
      'E' -> Orientation.East(),
      'W' -> Orientation.West()
    )

  }

  implicit val sizeIO: IO[Size] = new SingleLineIO[Size] {

    override val tt: TypeTag[Size]  = typeTag[Size]

    val Pattern: Regex  = "([0-9]+) ([0-9]+)".r

    override def readLine(line: String): Try[Size] = line match {
      case Pattern(width, height) => Success(Size(width.toInt + 1, height.toInt + 1))
      case _ => Failure(new Exception("Unable to parse size"))
    }

    override def writeLine(size: Size): Try[String] = Success(s"${size.width - 1} ${size.height - 1}")

  }


  implicit val positionIO: IO[Position] = new SingleLineIO[Position] {

    override val tt: TypeTag[Position]  = typeTag[Position]

    val Pattern: Regex  = "([0-9]+) ([0-9]+)".r

    override def readLine(line: String): Try[Position] = line match {
      case Pattern(x, y) => Success(Position(x.toInt, y.toInt))
      case _ => Failure(new Exception("Unable to parse size"))
    }

    override def writeLine(position: Position): Try[String] = Success(s"${position.x} ${position.y}")

  }

  implicit val stateIO: IO[State] = new SingleLineIO[State] {

    override val tt: TypeTag[State]  = typeTag[State]

    val Pattern: Regex = "([0-9 ]+) ([A-Z])".r

    override def readLine(line: String): Try[State] = line match {
      case Pattern(positionPart, orientationPart) => for {
        position <- IO.read[Position](positionPart)
        orientation <- IO.read[Orientation](orientationPart)
      } yield State(position, orientation)
      case _ => Failure(new Exception("Unable to parse state"))
    }

    override def writeLine(state: State): Try[String] = for {
      position <- IO.write[Position](state.position)
      orientation <- IO.write[Orientation](state.orientation)
    } yield s"${position.lines.head} ${orientation.lines.head}"

  }

  implicit val statesIO: IO[Seq[State]] = new IO[Seq[State]] {

    override def readLines(lines: Seq[String]): Try[Seq[State]] = ???

    override def writeLines(states: Seq[State]): Try[Seq[String]] = states.map(IO.write(_).map(_.lines)).toTrySeq()

  }

  implicit val programIO: IO[Program] = new IO[Program] {

    def readLines(lines: Seq[String]): Try[Program] = lines match {
      case Seq(firstLine, otherLines @ _ *) => for {
        initialState <- IO.read[State](firstLine)
        actions <- IO.read[Seq[Action]](otherLines)
      } yield Program(initialState, actions)
      case _ => Failure(new Exception("Unable to parse program"))
    }

    def writeLines(program: Program): Try[Seq[String]] = for {
      initialStateOutput <- IO.write[State](program.initialState)
      actionsOutput <- IO.write[Seq[Action]](program.actions)
    } yield initialStateOutput.lines ++ actionsOutput.lines

  }

  implicit val programsIO: IO[Seq[Program]] = new IO[Seq[Program]] {

    override def readLines(lines: Seq[String]): Try[Seq[Program]] = {
      lines.sliding(2, 2).map(IO.read[Program](_)).foldLeft[Try[Seq[Program]]](Success(Seq())) { (tryPrograms, tryProgram) =>
        for {
          programs <- tryPrograms
          program <- tryProgram
        } yield programs :+ program
      }
    }

    override def writeLines(programs: Seq[Program]): Try[Seq[String]] = programs.map(IO.write[Program](_).map(_.lines)).toTrySeq()
  }

  implicit val sizeAndProgramsIO: IO[(Size, Seq[Program])] = new IO[(Size, Seq[Program])] {

    def readLines(lines: Seq[String]): Try[(Size, Seq[Program])] = lines match {
      case Seq(firstLine, otherLines@_ *) => for {
        size <- IO.read[Size](firstLine)
        programs <- IO.read[Seq[Program]](otherLines)
      } yield (size, programs)
    }

    def writeLines(sizeAndPrograms: (Size, Seq[Program])): Try[Seq[String]] = {
      val (size, programs) = sizeAndPrograms
      for {
        sizeOutput <- IO.write[Size](size)
        programsOutput <- IO.write[Seq[Program]](programs)
      } yield sizeOutput.lines ++ programsOutput.lines
    }
  }
}

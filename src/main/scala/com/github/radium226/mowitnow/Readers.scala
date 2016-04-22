package com.github.radium226.mowitnow

import com.github.radium226.io.{IO, MappingBasedReader, Reader, SingleLineReader}
import Action._

import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

object Readers {

  implicit val action: Reader[Action] = new MappingBasedReader[Action] {

    val Mapping = Map(
      'A' -> MoveForward(),
      'D' -> TurnRight(),
      'G' -> TurnLeft()
    )

  }

  implicit val actions: Reader[Seq[Action]] = new SingleLineReader[Seq[Action]] {

    override def read(line: String): Try[Seq[Action]] = {
      line.sliding(1).map { IO.read[Action](_) }.foldLeft[Try[Seq[Action]]](Success(Seq())) { (tryActions, tryAction) =>
        for {
          actions <- tryActions
          action <- tryAction
        } yield actions :+ action
      }
    }

  }

  implicit val orientation: Reader[Orientation] = new MappingBasedReader[Orientation] {

    override val Mapping = Map(
      'N' -> Orientation.North(),
      'S' -> Orientation.South(),
      'E' -> Orientation.East(),
      'W' -> Orientation.West()
    )

  }

  implicit val size: Reader[Size] = new SingleLineReader[Size] {

    val Pattern: Regex  = "([0-9]+) ([0-9]+)".r

    override def read(line: String): Try[Size] = line match {
      case Pattern(width, height) => Success(Size(width.toInt, height.toInt))
      case _ => Failure(new Exception("Unable to parse size"))
    }

  }


  implicit val position: Reader[Position] = new SingleLineReader[Position] {

    val Pattern: Regex  = "([0-9]+) ([0-9]+)".r

    override def read(line: String): Try[Position] = line match {
      case Pattern(x, y) => Success(Position(x.toInt, y.toInt))
      case _ => Failure(new Exception("Unable to parse size"))
    }

  }

  implicit val state: Reader[State] = new SingleLineReader[State] {

    val Pattern: Regex = "([0-9 ]+) ([A-Z])".r

    override def read(line: String): Try[State] = line match {
      case Pattern(positionPart, orientationPart) => for {
        position <- IO.read[Position](positionPart)
        orientation <- IO.read[Orientation](orientationPart)
      } yield State(position, orientation)
      case _ => Failure(new Exception("Unable to parse state"))
    }

  }

  implicit val program: Reader[Program] = new Reader[Program] {

    def read(lines: Seq[String]): Try[Program] = lines match {
      case Seq(firstLine, otherLines @ _ *) => for {
        initialState <- IO.read[State](firstLine)
        actions <- IO.read[Seq[Action]](otherLines)
      } yield Program(initialState, actions)
      case _ => Failure(new Exception("Unable to parse program"))
    }

  }

  implicit val programs: Reader[Seq[Program]] = new Reader[Seq[Program]] {

    override def read(lines: Seq[String]): Try[Seq[Program]] = {
      lines.sliding(2, 2).map { IO.read[Program](_) }.foldLeft[Try[Seq[Program]]](Success(Seq())) { (tryPrograms, tryProgram) =>
        for {
          programs <- tryPrograms
          program <- tryProgram
        } yield programs :+ program
      }
    }

  }

  implicit val sizeAndPrograms: Reader[(Size, Seq[Program])] = new Reader[(Size, Seq[Program])] {

    def read(lines: Seq[String]): Try[(Size, Seq[Program])] = lines match {
      case Seq(firstLine, otherLines @ _ *) => for {
        size <- IO.read[Size](firstLine)
        programs <- IO.read[Seq[Program]](otherLines)
      } yield (size, programs)
    }

  }

}

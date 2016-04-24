package com.github.radium226.io

import scala.util.Try

import com.github.radium226.util.Implicits._
import com.typesafe.scalalogging.LazyLogging

import com.github.radium226.util._

import scala.reflect.runtime.universe._

/**
  * The purpose of this trait is to separate the reading and writing model classes from the classes themselves
  * @tparam T The type of the class to be read / written
  */
trait IO[T] {

  /**
    * Read lines and produce an instance of a model class
    * @param lines The lines to read
    * @return An instance of the model class
    */
  def readLines(lines: Seq[String]): Try[T]

  /**
    * Write lines from an instance of a model class
    * @param t The instance of the model class
    * @return The written lines
    */
  def writeLines(t: T): Try[Seq[String]]

}

trait SingleLineIO[T] extends IO[T] with LazyLogging {

  val tt: TypeTag[T]

  def exceptionForReadLinesFailure(lines: Seq[String]) = {
    val message = s"Unable to read ${typeTagToString(tt)} from line ${lines.headOption.getOrElse("")}"
    new Exception(message)
  }

  def readLines(lines: Seq[String]): Try[T] = {
    for {
      firstLine <- lines.headOption.toTry(new Exception(s"There is no line to read ${typeTagToString(tt)}"))
      t <- readLine(firstLine)
    } yield t
  }

  def readLine(line: String): Try[T]

  def writeLines(t: T): Try[Seq[String]] = writeLine(t).map(Seq(_))

  def writeLine(t: T): Try[String]

}

trait SingleCharIO[T] extends SingleLineIO[T] {

  def exceptionForReadLineFailure(line: String)  = {
    val message = s"Unable to read ${typeTagToString(tt)} from char ${line.headOption.getOrElse("")}"
    new Exception(message)
  }

  def readLine(line: String): Try[T] = {
    for {
      firstChar <- line.headOption.toTry(new Exception(s"There no char to read ${typeTagToString(tt)}"))
      t <- readChar(firstChar)
    } yield t
  }

  def readChar(char: Char): Try[T]

  def writeChar(t: T): Try[Char]

  def writeLine(t: T): Try[String] = writeChar(t).map(_.toString)

}

trait MappingBasedIO[T] extends SingleCharIO[T] {

  def exceptionForReadCharFailure(char: Char) = {
    val message = s"Unable to read ${typeTagToString(tt)} from char ${char}"
    new Exception(message)
  }

  def exceptionForWriteCharFailure(t: T) = {
    val message = s"Unable to write char from ${t}"
    new Exception(message)
  }

  val Mapping: Map[Char, T]

  def readChar(char: Char): Try[T] = Mapping.get(char).toTry(exceptionForReadCharFailure(char))

  def writeChar(t: T): Try[Char] = Mapping.map(_.swap).get(t).toTry(exceptionForWriteCharFailure(t))

}

/**
  * The purpose of this class is to read and write model classes from / to lines
  */
object IO extends LazyLogging {

  /**
    * Read an input to a model class instance
    * @param input The input to read
    * @param io A IO instance used to do the actual reading from the lines
    * @tparam T The type of the model class to be returned
    * @return An instance of the model class
    */
  def read[T](input: Input)(implicit io: IO[T]): Try[T] = {
    val lines = input.lines
    io.readLines(lines).map({ t =>
      logger.debug(s"Reading ${lines} as ${t}")
      t
    })
  }

  /**
    * Write a model class instance to an output
    * @param t The model class instance
    * @param io An IO instance used to do the actual writing from the lines
    * @tparam T The type of the model class to be written
    * @return An instance of the model class
    */
  def write[T](t: T)(implicit io: IO[T]): Try[Output] = {
    io.writeLines(t).map({ lines =>
      logger.debug(s"Writing ${t} as {line}")
      fromSeq(lines)
    })
  }

}

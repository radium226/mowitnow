package com.github.radium226.io

import scala.util.{ Try }
import com.github.radium226.util.Implicits._

trait IO[T] {

  def read(lines: Seq[String]): Try[T]

  def write(t: T): Try[Seq[String]]

}


trait SingleLineIO[T] extends IO[T] {

  def read(lines: Seq[String]): Try[T] = {
    val t: Option[T] = for {
      firstLine <- lines.headOption
      t <- readLine(firstLine).toOption
    } yield t
    t.toTry(new Exception("Unable to parse single line"))
  }

  def readLine(line: String): Try[T]

  def write(t: T): Try[Seq[String]] = writeLine(t).map(Seq(_))

  def writeLine(t: T): Try[String]

}

trait SingleCharIO[T] extends SingleLineIO[T] {

  def readLine(line: String): Try[T] = {
    val t: Option[T] = for {
      firstChar <- line.headOption
      t <- readChar(firstChar).toOption
    } yield t
    t.toTry(new Exception("Unable to parse single character"))
  }

  def readChar(char: Char): Try[T]

  def writeChar(t: T): Try[Char]

  def writeLine(t: T): Try[String] = writeChar(t).map(_.toString)

}

trait MappingBasedIO[T] extends SingleCharIO[T] {

  val Mapping: Map[Char, T]

  def readChar(char: Char): Try[T] = Mapping.get(char).toTry(new Exception("Unable to parse using mapping"))

  def writeChar(t: T): Try[Char] = Mapping.map(_.swap).get(t).toTry(new Exception("Unable to write char"))

}

object IO {

  def read[T](lines: Seq[String])(implicit io: IO[T]): Try[T] = io.read(lines)

  def read[T](line: String)(implicit io: IO[T]): Try[T] = read(Seq(line))(io)

  def write[T](t: T)(implicit io: IO[T]): Try[Seq[String]] = io.write(t)

}

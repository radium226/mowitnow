package com.github.radium226.io

import com.github.radium226.util.Implicits._

import scala.util.Try

trait Reader[T] {

  def read(lines: Seq[String]): Try[T]

}

trait SingleLineReader[T] extends Reader[T] {

  def read(lines: Seq[String]): Try[T] = {
    val t: Option[T] = for {
      firstLine <- lines.headOption
      t <- read(firstLine).toOption
    } yield t
    t.toTry(new Exception("Unable to parse single line"))
  }

  def read(line: String): Try[T]

}

trait SingleCharReader[T] extends SingleLineReader[T] {

  def read(line: String): Try[T] = {
    val t: Option[T] = for {
      firstChar <- line.headOption
      t <- read(firstChar).toOption
    } yield t
    t.toTry(new Exception("Unable to parse single character"))
  }

  def read(char: Char): Try[T]

}

trait MappingBasedReader[T] extends SingleCharReader[T] {

  val Mapping: Map[Char, T]

  def read(char: Char): Try[T] = Mapping.get(char).toTry(new Exception("Unable to parse using mapping"))

}

package com.github.radium226.io

import scala.util.{Try}

object IO {

  def read[T](lines: Seq[String])(implicit reader: Reader[T]): Try[T] = reader.read(lines)

  def read[T](line: String)(implicit reader: Reader[T]): Try[T] = read(Seq(line))(reader)

}

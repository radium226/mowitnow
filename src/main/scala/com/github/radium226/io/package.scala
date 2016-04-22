package com.github.radium226

package object io {

  import java.io.InputStream

  import scala.io.Source

  sealed trait LinesWrapper {

    val lines: Seq[String]

    override def equals(any: Any): Boolean = {
      any match {
        case that:LinesWrapper => that.lines.equals(lines)
      }
    }

  }

  implicit def fromInputStream(inputStream: InputStream): LinesWrapper = new LinesWrapper {

    val lines = Source.fromInputStream(inputStream).getLines().toSeq

  }

  implicit def fromString(string: String): LinesWrapper = new LinesWrapper {

    val lines = string.trim.split("\n").toSeq

  }

  implicit def fromSeq(seq: Seq[String]): LinesWrapper = new LinesWrapper {

    val lines = seq

  }

  type Output = LinesWrapper

  type Input = LinesWrapper

}

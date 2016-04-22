package com.github.radium226

import io._
import mowitnow._
import mowitnow.io._
import java.io._

import scala.util.{Failure, Success, Try}
import scopt.OptionParser
import scopt.OptionParser._

object MowItNow {

  case class Config(inputStream: InputStream, outputStream: OutputStream)

  def parse(arguments: Array[String]): Option[Config] = {
    new OptionParser[Config]("MowItNow") {
      opt[String]('o', "output") action { (output, config) =>
        val outputStream = output match {
          case "-" => System.out
          case path => new FileOutputStream(new File(path))
        }
        config.copy(outputStream = outputStream)
      } optional()
      arg[String]("input") action { (input, config) =>
        val inputStream = input match {
          case "-" => System.in
          case path => new FileInputStream(new File(path))
        }
        config.copy(inputStream = inputStream)
      } optional()
    }.parse(arguments, Config(inputStream = System.in, outputStream = System.out))
  }

  def main(arguments: Array[String]): Unit = {
    parse(arguments) match {
      case Some(config) =>
        println("Yay!")

      case None =>
        println("Wasted...")
    }

  }

}

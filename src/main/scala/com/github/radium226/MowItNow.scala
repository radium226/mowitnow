package com.github.radium226

import io._
import mowitnow._
import mowitnow.io._
import java.io._

import scala.util.{Failure, Success, Try}
import scopt.OptionParser
import scopt.OptionParser._

object MowItNow {

  val Success = 0
  val Failure = 1

  def exit(status: Int): Unit = System.exit(status)

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
      case Some(config) => {
        val tryOutput = for {
          sizeAndPrograms <- IO.read[(Size, Seq[Program])](config.inputStream)
          finalStates <- Mower.mow(sizeAndPrograms)
          output <- IO.write(finalStates)
        } yield output

        tryOutput match {
          case Success(output) => {
            val printer = new PrintStream(config.outputStream)
            output.lines.foreach(printer.println(_))
            printer.close()
            exit(Success)
          }
          case Failure(exception) => {
            exception.printStackTrace(System.err)
            exit(Failure)
          }
        }
      }
      case None => exit(Failure)
    }

  }

}

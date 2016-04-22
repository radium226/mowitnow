package com.github.radium226

import io._
import mowitnow._
import mowitnow.io._
import java.io._

import scala.util.{Failure, Success, Try}
import scopt.OptionParser
import scopt.OptionParser._

object MowItNow {

  val Good = 0
  val Bad = 1

  def exit(status: Int): Unit = System.exit(status)

  case class Config(inputStream: InputStream, outputStream: OutputStream, debug: Boolean)

  def parse(arguments: Array[String]): Option[Config] = {
    new OptionParser[Config]("MowItNow") {
      opt[String]('o', "output") action { (output, config) =>
        val outputStream = output match {
          case "-" => System.out
          case path => new FileOutputStream(new File(path))
        }
        config.copy(outputStream = outputStream)
      } optional()

      opt[Unit]('d', "debug") action { (_, config) =>
        config.copy(debug=true)
      } hidden()

      arg[String]("input") action { (input, config) =>
        val inputStream = input match {
          case "-" => System.in
          case path => new FileInputStream(new File(path))
        }
        config.copy(inputStream = inputStream)
      } optional()
    }.parse(arguments, Config(inputStream = System.in, outputStream = System.out, debug=false))
  }

  def main(arguments: Array[String]): Unit = {
    parse(arguments) match {
      case Some(config) => {
        val tryOutput = for {
          sizeAndPrograms <- IO.read[(Size, Seq[Program])](config.inputStream)
          finalStates <- Mower.mow(sizeAndPrograms)
          output <- IO.write[Seq[State]](finalStates)
        } yield output

        tryOutput match {
          case Success(output) => {
            val printer = new PrintStream(config.outputStream)
            output.lines.foreach(printer.println(_))
            printer.close()
            exit(Good)
          }
          case Failure(exception) => {
            if (config.debug) exception.printStackTrace(System.err) else System.err.println(exception.getMessage)
            exit(Bad)
          }
        }
      }
      case None => exit(Bad)
    }

  }

}

package com.github.radium226.util

import scala.util.{Failure, Success, Try}

object Implicits {

  implicit class OptionToTry[T](option: Option[T]) {

    def toTry(throwable: Throwable): Try[T] = option.fold[Try[T]](Failure(throwable))(Success(_))

  }

}

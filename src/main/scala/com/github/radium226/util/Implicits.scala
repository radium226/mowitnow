package com.github.radium226.util

import scala.util.{Failure, Success, Try}

object Implicits {

  implicit class OptionToTry[T](option: Option[T]) {

    def toTry(throwable: Throwable): Try[T] = option.fold[Try[T]](Failure(throwable))(Success(_))

  }

  implicit class SeqTrySeqToTrySeq[T](seq: Seq[Try[Seq[T]]]) {

    def toTrySeq(): Try[Seq[T]] = {
      seq.foldLeft[Try[Seq[T]]](Success(Seq())) { (trySeq, tryItem) =>
        for {
          seq <- trySeq
          item <- tryItem
        } yield seq ++ item
      }
    }
  }

}

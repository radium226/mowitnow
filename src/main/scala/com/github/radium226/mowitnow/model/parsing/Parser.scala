package com.github.radium226.mowitnow.model.parsing

import scala.util.Try

trait Parser[P] {

  def parse(text: String): Try[P]

}

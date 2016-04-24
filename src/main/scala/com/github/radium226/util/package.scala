package com.github.radium226

import scala.reflect.runtime.universe._

package object util {


  def typeTagToString(tt: TypeTag[_]): String = {
    typeToString(tt.tpe)
  }

  def typeToString(t: Type): String = {
    val l = t.typeArgs.map(typeToString(_))
    t.typeSymbol.asClass.name.toString + (if (l.isEmpty) "" else ("[" + l.mkString(", ") + "]"))
  }

}

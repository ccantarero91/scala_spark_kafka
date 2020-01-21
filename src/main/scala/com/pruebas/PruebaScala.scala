package com.pruebas

object PruebaScala {
  def main(args : Array[String]) : Unit = {
    args.foreach(println(_))
  }

  def devuelveMayusculas(s: String) : String = s.toUpperCase


}

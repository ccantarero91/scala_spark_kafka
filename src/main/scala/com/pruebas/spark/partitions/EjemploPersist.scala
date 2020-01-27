package com.pruebas.spark.partitions

import org.apache.spark.sql.{SaveMode, SparkSession}

object EjemploPersist {

  def main(args: Array[String]): Unit = {
    val spark : SparkSession = SparkSession.builder()
      .appName("Ejemplo Persist")
      .config("spark.master", "local")
      .getOrCreate()
    import spark.implicits._
    val accounts = spark.read.table("accounts")

    //NO FUNCIONA PORQUE NO LA HEMOS CREADO LA TABLA AQUI, EL PROFESOR LO RESUELVE ACCEDIENDO DESDE LA SHELL
  }

}

package com.pruebas.spark.partitions

import org.apache.spark.sql.{SaveMode, SparkSession}

object EjemploParticiones {

  def main(args: Array[String]): Unit = {
    val spark : SparkSession = SparkSession.builder()
      .appName("Ejemplo Particiones")
      .config("spark.master", "local")
      .getOrCreate()
    import spark.implicits._
    val valores = (1 to 10).toList
    val cifrasDf = valores.toDF("numeros")
    println(s"particiones ${cifrasDf.rdd.partitions.length}")

    cifrasDf.write.mode(SaveMode.Overwrite).json("hdfs://localhost/user/cloudera/numeros")
  }
}

package com.pruebas.stdstreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object EjemploStreaming {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("StreamingApp")
      .getOrCreate()

    //definimos donde se encuentran los archivos source
    val inputDir = "file:/tmp/devsh-streaming"

    //definimos el esquema de los json que vamos a procesar
    val schema = StructType(
      List(
         StructField("acct_num", IntegerType)
        ,StructField("dev_id", StringType)
        ,StructField("phone", StringType)
        ,StructField("model", StringType)
      )
    )

    //creamos el DataStreamReader
    val activatiosDf = spark.readStream.schema(schema).json(inputDir)

    activatiosDf.printSchema() //para comprobarlo

    //leemos los datos y los mostramos en la consola en modo append, es decir, solo aparecen los nuevos
    val activationsQuery =
      activatiosDf
        .writeStream
        .outputMode("append")
        .option("truncate", "false")
        .format("console")
        .start()

    //con esto se queda esperando a que le lanzemos la señal de kill
    activationsQuery.awaitTermination()

    //una vez recibe la seña de kill salimos ordenadamentee
    activationsQuery.stop()
    spark.stop()
  }
}

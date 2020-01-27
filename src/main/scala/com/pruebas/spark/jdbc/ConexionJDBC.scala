package com.pruebas.spark.jdbc

import java.util.Properties

import org.apache.spark.sql.{Encoders, SparkSession}

case class Movie(id : Long, name: String, year: Int)


object ConexionJDBC {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName("Ejemplo JDBC")
      .config("spark.master", "local")
      .config("registerKyroClasses", "classof[Movie]")
      .getOrCreate()

    //esto se puede hacer mejor en el sparksession como viene arriba
    //conf.registerKyroClasses(classOf[Movie])
/*    //importamos los encoders
    import  org.apache.spark.sql.Encoders
    //le asignamos el serializador de Kyro
    Encoders.kryo[Movie]
    // aplicamos el serializador
    val mvEncoder = Encoders.product[Movie]
*/

    //importamos los implicitos de spark
    import spark.implicits._

    //definimos variables de conexion
    val dbURL = "jdbc:mysql://localhost/movielens?user=cloudera&password=cloudera"

    //objeto de proiedades
    val props: Properties = new Properties()
    props.put("driver", "com.mysql.jdbc.Driver")

    //intemoas la conexion a MySql
    val moviesDs = spark.read.jdbc(dbURL, "movie", props).as[Movie]

    moviesDs.cache()
    moviesDs.printSchema()
    moviesDs.show()
    println(s"cuenta ${moviesDs.count()}")
//    println(s"cuenta ${mvEncoder.clsTag}")
    println(s"particiones ${moviesDs.rdd.partitions.length}")

    //cerramos la sparkSession
    spark.close()
  }

}

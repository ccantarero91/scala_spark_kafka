package com.pruebas.stdstreaming

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object IntegracionKafkaSpark {

  def main(args: Array[String]): Unit = {
    val KAFKA_TOPIC_NAME = "events"
    val KAFKA_OUTPUT_TOPIC_NAME = "notifications"
    val KAFKA_BOOTSTRAP_SERVERS = "quickstart.cloudera:9092"

    val spark =
      SparkSession
        .builder
        .master("local[*]")
        .appName("StreamingAppKafka")
        .getOrCreate()

    //definimos el nivel de log a ERROR
    spark.sparkContext.setLogLevel("ERROR")

    //nos conectamos a Kafka
    val tx_detail_df =
      spark
        .readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS)
        .option("subscribe", KAFKA_TOPIC_NAME)
        .option("startingOffsets", "latest") //no cogemos la prueba de antes
        .load()


    //mostramos el esquema para saber que esta ok
    tx_detail_df.printSchema()

    //casteamos a String la key y el value
    val tx_detail_df_1 = tx_detail_df.selectExpr(
      "CAST(value as STRING)",
      "CAST(timestamp as TIMESTAMP)"
    )

    //definimos unn esquema para el JSON que recibimos
    val tx_detail_schema = StructType(
      List(
        StructField("tx_id", StringType)
        ,StructField("tx_card_type", StringType)
        ,StructField("tx_amount", StringType)
        ,StructField("tx_datetime", StringType)
      )
    )
    import org.apache.spark.sql.functions._

    //seleccionamos las columnas
    val tx_detail_df_2: DataFrame =
      tx_detail_df_1
        .select(
          from_json(col("value"),tx_detail_schema).alias("tx_detail"),
          col("timestamp")
        )


    import org.apache.spark.sql.streaming._

    val tx_detail_df_3 =
      tx_detail_df_2
        .select(
          "tx_detail.*",
          "timestamp"
        )


    val tx_detail_df_4 =
      tx_detail_df_3
        .groupBy("tx_card_type")
        .agg(
          sum("tx_amount").as("total_tx_amount")
        )

    tx_detail_df_4.printSchema()

    val tx_detail_df_5 =
      tx_detail_df_4
        .withColumn("key", lit(100))
        .withColumn(
          "value",
          concat(
            lit("{'tx_card_type': '"),
            col("tx_card_type"),
            lit("', 'total_tx_amount: '"),
            col("total_tx_amount").cast("string"),
            lit("'}")
          )
        )

    tx_detail_df_5.printSchema()

    val trans_detail =
      tx_detail_df_5
        .writeStream
        .trigger(Trigger.ProcessingTime("1 seconds"))
        .outputMode("update")
        .option("truncate", "false")
        .format("console")
        .start()

    //guardamos los resultados en el topic de notifications
    val trans_detail_ws1 =
      tx_detail_df_5
        .selectExpr(
      "CAST(key as STRING)",
              "CAST(value as STRING)"
        )
        .writeStream
        .format("kafka")
        .option("kafka.bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS)
        .option("topic", KAFKA_OUTPUT_TOPIC_NAME)
        .trigger(Trigger.ProcessingTime("1 seconds"))
        .outputMode("update")
        .option("checkpointLocation", "/tmp/checkpoint1")
        .start()

    trans_detail.awaitTermination()
    println("StreamingAppKafka finished")
  }

}

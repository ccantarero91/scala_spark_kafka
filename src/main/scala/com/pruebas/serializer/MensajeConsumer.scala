package com.pruebas.serializer

import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.util.Try

object MensajeConsumer {
  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put("group.id", "test")
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer",
      "com.pruebas.serializer.UsuarioDeserializador")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")

    val consumer = new KafkaConsumer[String, Usuario](props)

    val topic = List("orange")

    Try {
      import collection.JavaConverters._
      consumer.subscribe(topic.asJava)
      println(s"---SUSCRIBED---")
      while(true){
        val records = consumer.poll(10)
        for(records <- records.asScala){
          println(s"---GETTING MESSAGE---")
          println(s"topic: [${records.topic()}]")
          println(s"partition: [${records.partition()}]")
          println(s"offset: [${records.offset()}]")
          println(s"key: [${records.key()}]")
          println(s"value: [${records.value()}]")
          println(s"------")
        }
      }

    }.getOrElse("Error Consumer")

  }
}

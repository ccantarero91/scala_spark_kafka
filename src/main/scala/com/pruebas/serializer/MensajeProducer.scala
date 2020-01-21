package com.pruebas.serializer

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Try

object MensajeProducer {

  def main(args: Array[String]): Unit = {
    val props:Properties = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",
      "com.pruebas.serializer.UsuarioSerializer")
    props.put("acks", "all")

    val producer = new KafkaProducer[String, Usuario](props)

    val topic = "orange"

    Try {
      for(i <- 0 to 10){
        val registro = new ProducerRecord[String, Usuario](topic, i.toString, Usuario(i, s"usuario_name_${i}"))
        val metatada = producer.send(registro)
        println(s"Enviando mensaje key [${registro.key()}]")
        println(s"Enviando mensaje value [${registro.value()}] ")
        println(s"metadatos topic [${metatada.get().topic()}]")
        println(s"metadatos partition [${metatada.get().partition()}]")
        println(s"metadatos offset[${metatada.get().offset()}]")
        println(s"------")
      }
    }.getOrElse("Error Producer")

  }

}

package com.pruebas.partitioner

import java.util

import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.record.InvalidRecordException

import scala.util.{Failure, Success, Try}

class CustomPartitioner extends Partitioner {

  val depto = "admon"
  val rrhh = "rrhh"

  override def partition(topic: String, key: Any, keyBytes: Array[Byte],
                         value: Any, valueBytes: Array[Byte], cluster: Cluster): Int = {
    val partitions = cluster.partitionsForTopic(topic)
    val numPartitions = partitions.size()
    val partition = 0

    (keyBytes, Try(key.asInstanceOf[String])) match {
      case (null, _) => throw new InvalidRecordException("Todos los mensajes deben de tener clave")
      case (_, Failure(exception)) => throw new InvalidRecordException(exception.getMessage)
      case (_, Success(s)) if s.startsWith(depto) => 0
      case (_, Success(s)) if s.startsWith(rrhh) => 1
      case _ => 2
    }
  }

  override def close(): Unit = {}

  override def configure(map: util.Map[String, _]): Unit = {}
}

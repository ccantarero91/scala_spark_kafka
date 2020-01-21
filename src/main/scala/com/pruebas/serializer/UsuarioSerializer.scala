package com.pruebas.serializer

import java.nio.ByteBuffer
import java.util

import org.apache.kafka.common.serialization.Serializer

import scala.util.Try

class UsuarioSerializer extends Serializer[Usuario] {
  private val encoding = "UTF8"

  override def configure(map: util.Map[String, _], b: Boolean): Unit = {}

  override def serialize(topic: String, data: Usuario): Array[Byte] = {
    Try {
      data match {
        case null => null
        case _ => {
          val seralizableName: Array[Byte] = data.nombre.getBytes(encoding)
          val sizeOfName = data.nombre.getBytes(encoding).length
          val buf = ByteBuffer.allocate(4 + 4 + sizeOfName + 4)
          buf.putInt(data.usuario_id)
          buf.putInt(sizeOfName)
          buf.put(seralizableName)
          buf.array()
        }
      }
    }.getOrElse{
      println("Error al serializar el usuario")
      null
    }
  }

  override def close(): Unit = {}
}

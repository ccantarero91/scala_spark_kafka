package com.pruebas.serializer

import java.nio.ByteBuffer
import java.util

import org.apache.kafka.common.serialization.Deserializer

import scala.util.Try

class UsuarioDeserializador extends Deserializer[Usuario]{
  private val encoding = "UTF8"

  override def configure(map: util.Map[String, _], b: Boolean): Unit = {}

  override def deserialize(s: String, bytes: Array[Byte]): Usuario = {
    bytes match {
      case null => println("Hemos recibido un null en el deserializador"); null
      case _ =>
        Try{
          val buf = ByteBuffer.wrap(bytes)
          val id = buf.getInt
          val sizeOfName: Int = buf.getInt()
          val nameBytes = new Array[Byte](sizeOfName)
          buf.get(nameBytes)
          val deserializedName: String = new String(nameBytes, encoding)
          Usuario(id, deserializedName)
        }.getOrElse{
          println("Error al deserializar Usuario")
          null
        }
    }
  }

  override def close(): Unit = ???
}

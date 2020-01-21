package com.pruebas.serializer

case class Usuario(usuario_id : Int, nombre : String) {
  override def toString: String = s"Usuario : [usuario_id : [${usuario_id}], nombre [$nombre]]"
}

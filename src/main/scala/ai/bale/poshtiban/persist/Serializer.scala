package ai.bale.poshtiban.persist

import java.nio.ByteBuffer

trait Serializer {
  def fromLong(value: Long): Array[Byte] =
    ByteBuffer.allocate(8).putLong(value).array()

  def fromString(value: String): Array[Byte] =
    value.getBytes.array

  def toString(bytes: Array[Byte]): String =
    bytes.map(_.toChar).mkString
}

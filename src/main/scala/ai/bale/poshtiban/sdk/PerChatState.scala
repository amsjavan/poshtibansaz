package ai.bale.poshtiban.sdk

import ai.bale.poshtiban.core.BaleBaseBot
import ai.bale.poshtiban.persist.{ RocksExtension, Serializer }
import com.bot4s.telegram.models.Message

trait PerChatState[S] extends Serializer {
  this: BaleBaseBot ⇒

  private val chatState = RocksExtension(_system).db

  def setChatState(value: String)(implicit msg: Message): Unit =
    chatState.foreach(_.put(fromLong(msg.chat.id), fromString(value)))

  def clearChatState(implicit msg: Message): Unit =
    chatState.foreach(_.remove(fromLong(msg.chat.id)))

  def withChatState(f: Option[String] ⇒ Unit)(implicit msg: Message): Unit = f(getChatState)

  def getChatState(implicit msg: Message): Option[String] =
    chatState.map(v ⇒ toString(v.get(fromLong(msg.chat.id))))

}
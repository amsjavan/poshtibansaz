package ai.bale.sdk

import com.bot4s.telegram.models.Message

trait PerChatState[S] {
  private val chatState = collection.mutable.Map[Long, S]()

  def setChatState(value: S)(implicit msg: Message): Unit = atomic {
    chatState.update(msg.chat.id, value)
  }

  def clearChatState(implicit msg: Message): Unit = atomic {
    chatState.remove(msg.chat.id)
  }

  private def atomic[T](f: ⇒ T): T = chatState.synchronized {
    f
  }

  def withChatState(f: Option[S] ⇒ Unit)(implicit msg: Message): Unit = f(getChatState)

  def getChatState(implicit msg: Message): Option[S] = atomic {
    chatState.get(msg.chat.id)
  }
}
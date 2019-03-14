package ai.bale.poshtiban.core

import ai.bale.poshtiban.commons.FileHelper
import ai.bale.poshtiban.persist.RocksExtension
import ai.bale.poshtiban.sdk.{ FixedBalePolling, PerChatState }
import akka.actor.ActorSystem
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.methods.{ SendInvoice, SendMessage }
import com.bot4s.telegram.models._
import ai.bale.poshtiban.misc.Strings._

class PoshtibanBot(token: String) extends BaleBaseBot(token)
  with FixedBalePolling
  with Commands
  with CommandHandler
  with PerChatState[String] {

  override val system: ActorSystem = _system

  val config = system.settings.config

  val yesButton = KeyboardButton(YES)
  val noButton = KeyboardButton(NO)
  val returnButton = KeyboardButton(RETURN)

  var userToken: String = _

  override def receiveMessage(message: Message): Unit = {
    implicit val msg: Message = message
    logger.debug("Input: {}", msg.text)

    if (msg.successfulPayment.isDefined) success()

    msg.text match {
      case Some(txt) if txt.startsWith("/start") ⇒ start()

      case Some(txt) if txt.contains(NEW_BOT) ⇒ input()

      case Some(txt) if txt.contains(MANAGE_BOT) ⇒ manage()

      case Some(txt) if getChatState.exists(_.equals("BOT_TOKEN")) ⇒ faq(txt)

      case Some(txt) if getChatState.exists(_.equals("FAQ")) ⇒ supporterNum(txt)

      case Some(txt) if getChatState.exists(_.equals("POSHTIBAN_NUMBER")) ⇒ sendInvoice(txt)

      case _ ⇒ logger.warn("Unmatched")
    }
  }
}
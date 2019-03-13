package ai.bale.poshtiban

import ai.bale.utils.FixedPolling
import akka.actor.ActorSystem
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.methods.{ SendInvoice, SendMessage }
import com.bot4s.telegram.models._

class PoshtibanBot(token: String) extends ExampleBot(token)
  with FixedPolling
  with Commands {

  override val system: ActorSystem = _system
  private val config = system.settings.config
  private def invoice(msg: Message): Unit = {
    request(SendInvoice(
      chatId = msg.source,
      title = "salam",
      description = "desc",
      payload = "d",
      providerToken = "6219861032590043", startParameter = "6219861032590043",
      currency = Currency.THB,
      prices = Array(
        LabeledPrice(
          label = "مالیات",
          100),
        LabeledPrice(
          label = "حمل و نقل",
          200))))
  }
  override def receiveMessage(message: Message): Unit = {
    implicit val msg: Message = message
    if (msg.successfulPayment.isDefined) {
      request(SendMessage(msg.source, config.getString("message.invoice.success")))
    }
    msg.text match {
      case Some(txt) if txt.startsWith("/start") ⇒
        request(SendMessage(msg.source, config.getString("message.start")))
        val newBot = KeyboardButton("ساخت بازوی پشتیبان")
        val manageBot = KeyboardButton("مدیریت بازوی ساخته شده")
        replyMd(
          text = "من باتم",
          replyMarkup = Some(ReplyKeyboardMarkup.singleRow(Seq(newBot, manageBot))))
    }
  }
}
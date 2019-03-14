package ai.bale.poshtiban.core

import ai.bale.poshtiban.commons.FileHelper
import ai.bale.poshtiban.persist.RocksExtension
import ai.bale.poshtiban.sdk.{ FixedBalePolling, PerChatState }
import akka.actor.ActorSystem
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.methods.{ SendInvoice, SendMessage }
import com.bot4s.telegram.models._

class PoshtibanBot(token: String) extends BaleBaseBot(token)
  with FixedBalePolling
  with Commands
  with PerChatState[String] {

  override val system: ActorSystem = _system

  import ai.bale.poshtiban.misc.Strings._

  val yesButton = KeyboardButton(YES)
  val noButton = KeyboardButton(NO)
  val returnButton = KeyboardButton(RETURN)

  var userToken: String = _

  override def receiveMessage(message: Message): Unit = {
    implicit val msg: Message = message
    logger.debug("Message text: {}", msg.text)

    if (msg.successfulPayment.isDefined) {
      request(SendMessage(msg.source, "بازوی شما در حال ایجاد شدن است"))
      Processor.removeOldDockers
      Thread.sleep(1000)
      DockerHelper.createDocker(msg.chat.id.toString, userToken)
      request(SendMessage(msg.source, "بازوی شما ایجاد شد."))
      FileHelper.deleteRecursively(new java.io.File("rocksdb"))
    }
    msg.text match {
      case Some(txt) if txt.startsWith("/start") ⇒
        val newBot = KeyboardButton(NEW_BOT)
        val manageBot = KeyboardButton(MANAGE_BOT)
        replyMd(
          text = START,
          replyMarkup = Some(ReplyKeyboardMarkup.singleRow(Seq(newBot, manageBot))))

      case Some(txt) if txt.contains(NEW_BOT) ⇒
        logger.debug("new bot state")
        request(SendMessage(msg.source, "لطفا توکن بازوی خود را وارد کنید"))
        setChatState("BOT_TOKEN")

      case Some(txt) if txt.contains(MANAGE_BOT) ⇒
        request(SendMessage(msg.source, "در حال آماده سازی..."))

      case Some(txt) if getChatState.exists(_.equals("BOT_TOKEN")) ⇒
        userToken = txt
        replyMd(
          text = "آیا بازوی شما دارای بخش سوالات متداول باشد؟",
          replyMarkup = Some(ReplyKeyboardMarkup.singleRow(Seq(yesButton, noButton, returnButton))))
        setChatState("FAQ")

      case Some(txt) if getChatState.exists(_.equals("FAQ")) ⇒
        if (txt.contains(YES)) {

        }
        val oneToFive = KeyboardButton("۱ تا ۵ نفر")
        val sixToTen = KeyboardButton("۶ تا ۱۰ نفر")
        val ten = KeyboardButton("بیش از ۱۰ نفر")
        replyMd(
          text = "چند نفر پشتیبان دارید؟",
          replyMarkup = Some(ReplyKeyboardMarkup.singleRow(Seq(oneToFive, sixToTen, ten, returnButton))))

        setChatState("POSHTIBAN_NUMBER")

      case Some(txt) if getChatState.exists(_.equals("POSHTIBAN_NUMBER")) ⇒
        request(SendInvoice(
          chatId = msg.source,
          title = "پشتیبان ساز",
          description = "هزینه راه اندازی",
          payload = "d",
          providerToken = "6219861032590043", startParameter = "6219861032590043",
          currency = Currency.THB,
          prices = Array(
            LabeledPrice(
              label = "سوالات متداول",
              200),
            LabeledPrice(
              label = "پشتیبان\u200Cها",
              300))))
        setChatState("PARDAKHT")

      case _ ⇒ logger.warn("Unmatched")
    }
  }
}
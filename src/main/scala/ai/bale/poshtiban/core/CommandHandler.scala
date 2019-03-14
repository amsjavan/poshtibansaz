package ai.bale.poshtiban.core

import ai.bale.poshtiban.commons.FileHelper
import ai.bale.poshtiban.misc.Strings._
import com.bot4s.telegram.methods.{ SendInvoice, SendMessage }
import com.bot4s.telegram.models._

trait CommandHandler {
  this: PoshtibanBot ⇒
  def start()(implicit msg: Message): Unit = {
    FileHelper.deleteRecursively(new java.io.File(config.getString("services.rocksdb.path")))
    val newBot = KeyboardButton(NEW_BOT)
    val manageBot = KeyboardButton(MANAGE_BOT)
    replyMd(
      text = START,
      replyMarkup = Some(ReplyKeyboardMarkup.singleRow(Seq(newBot, manageBot))))
  }

  def input()(implicit msg: Message): Unit = {
    request(SendMessage(msg.source, INPUT_TOKEN))
    setChatState("BOT_TOKEN")
  }

  def manage()(implicit msg: Message): Unit = {
    request(SendMessage(msg.source, IN_PROGRESS))
  }

  def faq(input: String)(implicit msg: Message): Unit = {
    userToken = input
    replyMd(
      text = HAS_FAQ,
      replyMarkup = Some(ReplyKeyboardMarkup.singleRow(Seq(yesButton, noButton, returnButton))))
    setChatState("FAQ")
  }

  def supporterNum(input: String)(implicit msg: Message): Unit = {
    if (input.contains(YES)) {
    }
    val oneToFive = KeyboardButton(ONE_TO_FIVE)
    val sixToTen = KeyboardButton(SIX_TO_TEN)
    val ten = KeyboardButton(MORE_THAN_TEN)
    replyMd(
      text = SUPPORTER_NUM,
      replyMarkup = Some(ReplyKeyboardMarkup.singleRow(Seq(oneToFive, sixToTen, ten, returnButton))))

    setChatState("POSHTIBAN_NUMBER")
  }

  def sendInvoice(input: String)(implicit msg: Message): Unit = {
    request(SendInvoice(
      chatId = msg.source,
      title = SUPPORT_MAKER,
      description = DEPLOY_COST,
      payload = "",
      providerToken = config.getString("payment.card-number"),
      startParameter = config.getString("payment.card-number"),
      currency = Currency.THB,
      prices = Array(
        LabeledPrice(
          label = FAQ,
          200),
        LabeledPrice(
          label = SUPPORTERS,
          300))))
    setChatState("PARDAKHT")
  }

  def success()(implicit msg: Message): Unit = {
    request(SendMessage(msg.source, CREATING))
    logger.info("Removing containers...")
    Processor.removeOldDockers
    Thread.sleep(1000)
    logger.info("Creating containers...")
    DockerHelper.createDocker(msg.chat.id.toString, userToken)
    request(SendMessage(msg.source, CREATE_SUCCESSFULLY))
    FileHelper.deleteRecursively(new java.io.File(config.getString("services.rocksdb.path")))
    start()
  }
}

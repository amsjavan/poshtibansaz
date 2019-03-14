package ai.bale.poshtiban.misc

import com.typesafe.config.ConfigFactory

object Strings {
  private val string = ConfigFactory.parseResources("string.conf")

  val START = string.getString("start")
  val NEW_BOT = string.getString("new-bot")
  val MANAGE_BOT = string.getString("manage-bot")

  val YES = "بله"
  val NO = "خیر"
  val RETURN = "بازگشت"
}

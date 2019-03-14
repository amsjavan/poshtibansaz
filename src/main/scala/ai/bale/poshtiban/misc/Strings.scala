package ai.bale.poshtiban.misc

import com.typesafe.config.ConfigFactory

object Strings {
  private val string = ConfigFactory.parseResources("string.conf")

  val START: String = string.getString("start")
  val NEW_BOT: String = string.getString("new-bot")
  val MANAGE_BOT: String = string.getString("manage-bot")

  val HAS_FAQ: String = string.getString("has-faq")
  val INPUT_TOKEN: String = string.getString("input-token")
  val CREATE_SUCCESSFULLY: String = string.getString("create-successfully")
  val CREATING: String = string.getString("creating")
  val IN_PROGRESS: String = string.getString("in-progress")

  val ONE_TO_FIVE: String = string.getString("on-to-five")
  val SIX_TO_TEN: String = string.getString("six-to-ten")
  val MORE_THAN_TEN: String = string.getString("more-than-ten")
  val SUPPORT_MAKER: String = string.getString("support-maker")
  val DEPLOY_COST: String = string.getString("deploy-cost")
  val FAQ: String = string.getString("faq")
  val SUPPORTERS: String = string.getString("supporters")
  val SUPPORTER_NUM: String = string.getString("supporter-num")

  val YES: String = string.getString("yes")
  val NO: String = string.getString("no")
  val RETURN: String = string.getString("return")

}

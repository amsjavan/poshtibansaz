import ai.bale.commons.BotConfig
import ai.bale.poshtiban.PoshtibanBot

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val config = BotConfig.load()
  val bot = new PoshtibanBot(config.getString("bot.token"))
  val eol = bot.run()
  Await.result(eol, Duration.Inf)
}
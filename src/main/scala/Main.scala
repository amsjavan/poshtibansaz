import ai.bale.poshtiban.PoshtibanBot

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val bot = new PoshtibanBot("ca0c0e3ff745f4977f5702743d6b0461d79c9063")
  val eol = bot.run()
  println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
  scala.io.StdIn.readLine()
  bot.shutdown() // initiate shutdown
  // Wait for the bot end-of-life
  Await.result(eol, Duration.Inf)
}
package ai.bale.poshtiban

import ai.bale.core.BaleAkkaHttpClient
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bot4s.telegram.api.TelegramBot
import slogging.{ LogLevel, LoggerConfig, PrintLoggerFactory }
import slogging._

abstract class ExampleBot(val token: String) extends TelegramBot {
  LoggerConfig.factory = SLF4JLoggerFactory()
  LoggerConfig.level = LogLevel.DEBUG

  implicit val _system: ActorSystem = ActorSystem()
  implicit val mt = ActorMaterializer()
  val client = new BaleAkkaHttpClient(token, telegramHost = "tapi.bale.ai")

}
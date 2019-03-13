package ai.bale.poshtiban

import ai.bale.core.BaleAkkaHttpClient
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bot4s.telegram.api.TelegramBot
import slogging.{ LogLevel, LoggerConfig, PrintLoggerFactory }

abstract class ExampleBot(val token: String) extends TelegramBot {
  LoggerConfig.factory = PrintLoggerFactory()
  // set log level, e.g. to TRACE
  LoggerConfig.level = LogLevel.TRACE

  implicit val _system: ActorSystem = ActorSystem()
  implicit val mt = ActorMaterializer()
  val client = new BaleAkkaHttpClient(token, telegramHost = "tapi.bale.ai")

}
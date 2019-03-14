package ai.bale.poshtiban.core
import scala.util.Try
import sys.process._

object Processor {
  def runDocker(path: String): String = {
    Try(s"docker-compose -f ${path} up -d" !!).getOrElse("")
  }

  def removeOldDockers: String = {
    Try("docker rm -f hrtc_support_bot".!!)
    Try("docker rm -f pg_hrtc_support_bot".!!).getOrElse("")
  }
}

package ai.bale.poshtiban.core
import sys.process._

object Processor {
  def runDocker(path: String): String = {
    s"docker-compose -f ${path} up -d" !!
  }
}

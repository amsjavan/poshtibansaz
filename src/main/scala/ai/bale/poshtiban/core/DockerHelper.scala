package ai.bale.poshtiban.core

import ai.bale.poshtiban.commons.FileHelper

object DockerHelper {
  def createDocker(name: String, token: String): Unit = {
    val content = FileHelper.niceFeedbackReadResource("docker-compose.yml")
    val newPath = FileHelper.writeToFile(content.replace("token-id", token), name)
    Processor.runDocker(newPath)
    Thread.sleep(5000)
  }

}

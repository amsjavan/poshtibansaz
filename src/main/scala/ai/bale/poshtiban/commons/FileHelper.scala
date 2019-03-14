package ai.bale.poshtiban.commons

import java.io.FileNotFoundException

import scala.io.Source
import scala.util.Try

object FileHelper {
  def niceFeedbackReadResource(resource: String): String =
    Try(Source.fromResource(resource).mkString).getOrElse(
      throw new FileNotFoundException(resource))

  def writeToFile(content: String, fileName: String): String = {
    // PrintWriter
    import java.io._
    val f = new File(fileName)
    val pw = new PrintWriter(f)
    pw.write(content)
    pw.close
    f.getPath
  }
}

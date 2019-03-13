package ai.bale.core

import akka.actor.ActorSystem
import akka.pattern.after
import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.models.Update

import scala.concurrent.Future
import scala.concurrent.duration._

trait FixedBalePolling extends BalePolling {
  val system: ActorSystem
  @volatile private var polling: Future[Unit] = _

  override def pollingGetUpdates(offset: Option[Long]): Future[Seq[Update]] = {
    after(1.second, system.scheduler) {
      super.pollingGetUpdates(offset)
    }
  }
}
package ai.bale.poshtiban.persist

import akka.actor.{ ExtendedActorSystem, Extension, ExtensionId }
import org.rocksdb.{ Options, RocksDB }

class RocksExtensionImpl(system: ExtendedActorSystem) extends Extension {

  private val rocksPath = system.settings.config.getString("services.rocksdb.path")

  // a static method that loads the RocksDB C++ library.
  RocksDB.loadLibrary()

  val db: Option[RocksDB] = try {
    val options = new Options().setCreateIfMissing(true)
    Some(RocksDB.open(options, rocksPath))
  } catch {
    case e: Throwable ⇒
      system.log.error(e, e.getMessage)
      None
  }

}

object RocksExtension extends ExtensionId[RocksExtensionImpl] {
  override def createExtension(system: ExtendedActorSystem): RocksExtensionImpl = new RocksExtensionImpl(system)
}

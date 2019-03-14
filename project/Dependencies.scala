package ai.bale

import sbt.Keys.libraryDependencies
import sbt._

object Dependencies {

  object V {
    val logback = "1.2.3"
    val sdk = "4.0.0-RC2"
    val slogging = "0.6.1"
    val slf4j = "1.7.+"
    val rocks = "5.17.2"
  }

  object Compile {
    val logback = "ch.qos.logback" % "logback-classic" % V.logback
    val coreSdk = "com.bot4s" %% "telegram-core" % V.sdk
    val akkaSdk = "com.bot4s" %% "telegram-akka" % V.sdk
    val slogging = "biz.enef" %% "slogging-slf4j" % V.slogging
    val slf4j = "org.slf4j" % "slf4j-simple" % V.slf4j
    val rocksDb = "org.rocksdb" % "rocksdbjni" % V.rocks

  }

  import Compile._

  val l = libraryDependencies

  val core =  l ++= Seq(
    logback,
    coreSdk,
    akkaSdk,
    slogging,
    slf4j,
    rocksDb
  )
}


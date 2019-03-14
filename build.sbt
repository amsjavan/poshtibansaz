import scalariform.formatter.preferences._

name := "poshtiban-saz"

version := "0.1"

scalaVersion := "2.12.8"

scalariformPreferences := scalariformPreferences.value
  .setPreference(RewriteArrowSymbols, true)
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(SpacesAroundMultiImports, true)

libraryDependencies ++= Seq(
  "com.bot4s" %% "telegram-core" % "4.0.0-RC2",
  "com.bot4s" %% "telegram-akka" % "4.0.0-RC2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
"biz.enef" %% "slogging-slf4j" % "0.6.1",
  "org.slf4j" % "slf4j-simple" % "1.7.+"
)
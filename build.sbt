import scalariform.formatter.preferences._
import ai.bale.Dependencies

enablePlugins(DockerPlugin)
enablePlugins(JavaServerAppPackaging)

name := "poshtibansaz"

scalaVersion := "2.12.8"

Dependencies.core

scalariformPreferences := scalariformPreferences.value
  .setPreference(RewriteArrowSymbols, true)
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(SpacesAroundMultiImports, true)

dockerBaseImage := "docker.bale.ai/openjdk:8"
packageName in Docker := "docker.bale.ai/hackathon/grpc"
version in Docker := (version in ThisBuild).value
dockerUpdateLatest := false


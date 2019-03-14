import scalariform.formatter.preferences._
import ai.bale.Dependencies

name := "poshtiban-saz"

version := "0.1"

scalaVersion := "2.12.8"

scalariformPreferences := scalariformPreferences.value
  .setPreference(RewriteArrowSymbols, true)
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(SpacesAroundMultiImports, true)

Dependencies.core
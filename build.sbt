enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % "0.0.0-26-gda81da6",
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

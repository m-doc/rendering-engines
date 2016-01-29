enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % "0.0.0-10-g4a61b77",
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

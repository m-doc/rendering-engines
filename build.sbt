enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % "0.0.0-22-gfd25c36",
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

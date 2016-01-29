enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % "0.0.0-23-ga79febf",
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % Version.fshell,
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

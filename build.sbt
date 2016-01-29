enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % "0.0.0-24-gd35fb64",
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  Library.fshell,
  Library.scalacheck % "test"
)

enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  Library.commonModel,
  Library.fshell,
  Library.scalacheck % "test"
)

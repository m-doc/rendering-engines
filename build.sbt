enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  MdocLibrary.commonModel,
  MdocLibrary.fshell,
  Library.scalacheck % "test"
)

enablePlugins(MdocPlugin)

name := "rendering-engines"

libraryDependencies ++= Seq(
  Library.fshell.copy(revision = "0.0.0-32-g38967c8"),
  Library.scalacheck % "test"
)

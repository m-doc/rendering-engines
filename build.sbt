enablePlugins(MdocPlugin)

name := "rendering-engines"

resolvers += "m-doc/maven" at "https://dl.bintray.com/m-doc/maven"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % "0.0.0-23-ga79febf",
  "org.scalacheck" %% "scalacheck" % Version.scalacheck % "test"
)

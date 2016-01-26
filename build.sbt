enablePlugins(BuildInfoPlugin)
enablePlugins(GitVersioning)

name := "rendering-engines"

organization := "org.m-doc"
bintrayOrganization := Some("m-doc")
homepage := Some(url("https://github.com/m-doc/rendering-engines"))
startYear := Some(2016)
licenses += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
scmInfo := Some(ScmInfo(homepage.value.get, "git@github.com:m-doc/rendering-engines.git"))

scalaVersion := "2.11.7"
crossScalaVersions := Seq("2.11.7", "2.10.6")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard"
)

resolvers += "m-doc's Bintray" at "https://dl.bintray.com/m-doc/maven"

libraryDependencies ++= Seq(
  "org.m-doc" %% "fshell" % "0.0.0-10-g4a61b77",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
)

publishMavenStyle := true

git.useGitDescribe := true

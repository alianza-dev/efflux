name := "efflux"

lazy val settings = Seq(
  version := "0.4.0",

  scalaVersion := "2.11.8",

  resolvers := Seq("Artifactory" at "http://lolhens.no-ip.org/artifactory/libs-release/"),

  libraryDependencies ++= Seq(
    "org.jboss.netty" % "netty" % "3.2.2.Final",
    "org.slf4j" % "slf4j-api" % "1.6.1"
  ),

  mainClass in Compile := None,

  scalacOptions ++= Seq("-Xmax-classfile-name", "254")
)

lazy val root = project.in(file("."))
  .enablePlugins(
    JavaAppPackaging,
    UniversalPlugin)
  .settings(settings: _*)
  .settings(
    sources in(Compile, doc) := Seq.empty,
    publishArtifact in(Compile, packageDoc) := false
  )

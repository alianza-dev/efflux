name := "efflux"

lazy val settings = Seq(
  version := "0.4.0",

  scalaVersion := "2.11.8",

  resolvers := Seq("Artifactory" at "http://lolhens.no-ip.org/artifactory/libs-release/"),

  crossPaths := false,

  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "io.netty" % "netty-all" % "4.0.42.Final",
    "junit" % "junit" % "4.12" % Test,
    "com.novocode" % "junit-interface" % "0.11" % Test
  ),

  testOptions += Tests.Argument(TestFrameworks.JUnit, "-q"),

  mainClass in Compile := None,

  scalacOptions ++= Seq("-Xmax-classfile-name", "254"),

  sources in(Compile, doc) := Seq.empty,
  publishArtifact in(Compile, packageDoc) := false
)

lazy val root = project.in(file("."))
  .enablePlugins(
    JavaAppPackaging,
    UniversalPlugin)
  .settings(settings: _*)

name := "mowitnow"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0-M15",
  "com.github.scopt" %% "scopt" % "3.4.0",
  "ch.qos.logback" %  "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"
)

coverageExcludedPackages := "com.github.radium226.MowItNow"

test in assembly := {}

mainClass in assembly := Some("com.github.radium226.MowItNow")
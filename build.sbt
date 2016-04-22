name := "mowitnow"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.0.0-M15", "com.github.scopt" %% "scopt" % "3.4.0")


coverageExcludedPackages := "com.github.radium226.MowItNow"
    
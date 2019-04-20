name := "scalacheck-workshop"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

scalacOptions += "-language:higherKinds"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.typelevel" %% "cats-core" % "1.5.0"
)

name := """platform-template"""
organization := "com.thiefspin"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  guice,
  "org.playframework.anorm" %% "anorm" % "2.6.5",
  "commons-codec" % "commons-codec" % "1.14",
  "org.bouncycastle" % "bcprov-jdk16" % "1.46",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)

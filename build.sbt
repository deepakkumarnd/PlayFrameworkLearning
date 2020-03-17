name := """eve"""
organization := "com.genesis"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// cassandra libraries
libraryDependencies += "com.datastax.oss" % "java-driver-core" % "4.5.0"
libraryDependencies += "com.datastax.oss" % "java-driver-query-builder" % "4.5.0"

// Adds additional packages into Twirl
// TwirlKeys.templateImports += "com.genesis.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.genesis.binders._"

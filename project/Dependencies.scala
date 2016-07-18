import Versions._
import sbt._

object Dependencies {


  val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaV

  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaV

  val cassandraDriver ="com.datastax.cassandra"  % "cassandra-driver-core" % cassandraDriverV exclude("org.xerial.snappy", "snappy-java")

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLogV

  val basicDeps = Seq(akkaHttpSprayJson, akkaActor,akkaSlf4j,cassandraDriver,scalaLogging)

}
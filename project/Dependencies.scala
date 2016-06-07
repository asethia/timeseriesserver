import Versions._
import sbt._

object Dependencies {

  val akkaStream = "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamV

  val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamV

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaV

  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaV

  val basicDeps = Seq(akkaStream,akkaHttpSprayJson, akkaActor,akkaSlf4j)

}
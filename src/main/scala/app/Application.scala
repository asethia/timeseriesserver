package app


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

/**
  * This is application provider
  * Created by Arun.Sethia on 17/07/16.
  */
abstract class Application(systemName: String) extends App {

  implicit val actorSystem: ActorSystem = ActorSystem(systemName)
  implicit val materialize: ActorMaterializer = ActorMaterializer()
  implicit val logger:Logger = Logger(LoggerFactory.getLogger("Startup"))

}

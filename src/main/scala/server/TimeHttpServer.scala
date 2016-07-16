package server

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import org.slf4j.Logger

import scala.concurrent.{ExecutionContext, Await}
import scala.concurrent.duration.FiniteDuration
import scala.util.control.NonFatal


/**
  * This is Http Server Class to create Http Server
  * with defined Http handler
  * Created by Arun Sethia on 16/07/16.
  */
class TimeHttpServer(port: Int = 8080, handler: Flow[HttpRequest, HttpResponse, Any])
                    (implicit logger: Logger, system: ActorSystem, materializer: ActorMaterializer) {

  private val httpAddress = "0.0.0.0"
  implicit val ec:ExecutionContext=system.dispatcher

  def run = {
    Http().bindAndHandle(handler, httpAddress, port).map(shutDownProcess)
  }

  def shutDownProcess(serverBinding: ServerBinding)(implicit system: ActorSystem, materializer: ActorMaterializer) = {
    logger.info("Shutdown - starting to unbind HTTP server")
    val unbindFuture = serverBinding.unbind()
    try {
      val t = Await.result(unbindFuture, FiniteDuration(2, TimeUnit.SECONDS))
      logger.info("HTTP server unbound successfully")
    } catch {
      case NonFatal(e) => logger.error(s"Unbinding HTTP server failed: ${e.getMessage}", e)
    }
    materializer.shutdown()
    system.terminate()
  }
}

/**
  * this is http server factory object
  */
object TimeHttpServer {
  def apply(port: Int, handler: Route)
           (implicit logger: Logger,system: ActorSystem, materializer: ActorMaterializer) = {

    val routeWithLogging = DebuggingDirectives.
      logRequestResult(TimeHttpServer.requestMethodAndResponseStatusAsInfo _)(handler)

    new TimeHttpServer(port, routeWithLogging)

  }

  /**
    * Request and reponse logging
    *
    * @param req
    * @return
    */
  private def requestMethodAndResponseStatusAsInfo(req: HttpRequest): Any => Option[LogEntry] = {
    case res: HttpResponse => Some(LogEntry(s"response url:${req.uri} , http method:${req.method}, response " +
      s"status:${res.status}", Logging.InfoLevel))
    case _ => Some(LogEntry(s"response url:${req.uri} , http method:${req.method}, response ", Logging.InfoLevel))
  }
}


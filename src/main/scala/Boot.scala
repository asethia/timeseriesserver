import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.io.Tcp.Bind
import akka.io.{Tcp, IO}
import app.Application
import com.typesafe.config.ConfigFactory
import handler.TCPHandler
import server.TimeHttpServer
import service.HttpService

/**
  * This is TCP server opens port on 127.0.0.1
  * by passing VM options -Dtcpport.
  * The TCPHanlder actor which will receive all incoming connection requests
  * in the form of [[akka.io.Tcp.Connected]] messages.
  *
  * This is main application class
  * Created by Arun.Sethia on 17/07/16.
  */
object Boot extends Application("TimeSeriesServer") with HttpService{

  implicit val system = ActorSystem()


  val config=ConfigFactory.load()

  IO(Tcp) ! Bind(handler = system.actorOf(TCPHandler.props, "tcpHandler"),
    localAddress = new InetSocketAddress("127.0.0.1", config.getInt("TCP_PORT")))


  TimeHttpServer(8080,serviceRoute).run
}

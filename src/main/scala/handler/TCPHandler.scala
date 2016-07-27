package handler

import akka.actor.{ActorLogging, Actor, Props}
import akka.io.Tcp._
import akka.routing.FromConfig


/**
  * This is TCP Handler; this actor which will receive all
  * incoming connection requests in the form of [[Connected]] messages.
  *
  * It sends Register message to the connection.
  * The register message holds message/connection handler object.
  *
  * Created by Arun Sethia on 27/05/16.
  */
object TCPHandler{
  def props=Props(classOf[TCPHandlerActor])
  val ConnectedMsg: String ="connected ....."
}

class TCPHandlerActor extends Actor with ActorLogging{

  val messageHandler=context.actorOf(FromConfig.props(MessageHandlerActor.props),"messageHandler")

  def receive:Receive={
    case b @ Bound(localAddress) => {
      println("I am here")
      log.warning(s"hello ${b.localAddress}")
    }
    case CommandFailed(_: Bind) => context stop self
    case c @ Connected(remote, local) => {
      val connection = sender()
      connection ! Register(messageHandler)
    }
  }
}

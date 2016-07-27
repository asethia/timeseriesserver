package handler

import akka.actor.{ActorLogging, Props, Actor}
import akka.io.Tcp.{Write, Received, PeerClosed}
import akka.util.ByteString


object MessageHandlerActor{
  def props=Props(new MessageHandlerActor())
}


/**
  * This is messagne handler, messages coming
  * from TCP client and sends response back to the client
  *
  * Created by Arun Sethia on 27/05/16.
  */
class MessageHandlerActor extends Actor with ActorLogging{
  def receive:Receive={
    case str:String=>{
      log.info(s"Message received $str")
    }
    case Received(data) => {
      val in=data.decodeString("UTF-8")
      log.info(s"Message received $in")
      sender() ! Write(ByteString("ok"))
    }
    case PeerClosed     => context stop self
  }
}
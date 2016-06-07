package handler

import akka.actor.{Props, Actor}
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
class MessageHandlerActor extends Actor{
  def receive:Receive={
    case str:String=>println(s"Message received $str")
    case Received(data) => {
      val in=data.decodeString("UTF-8")
      println(s"Message received $in")
      sender() ! Write(ByteString("ok"))
    }
    case PeerClosed     => context stop self
  }
}
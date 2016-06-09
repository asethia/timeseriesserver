package server

import java.net.InetSocketAddress

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import com.typesafe.config.ConfigFactory

import handler.TCPHandler


/**
  * This is TCP server opens port on 127.0.0.1
  * by passing VM options -Dtcpport.
  * The TCPHanlder actor which will receive all incoming connection requests
  * in the form of [[akka.io.Tcp.Connected]] messages.
  *
  * Created by Arun Sethia on 07/06/16.
  */
object IOTcp extends App {

  implicit val system = ActorSystem()

  val config=ConfigFactory.load()

  IO(Tcp) ! Bind(handler = system.actorOf(TCPHandler.props, "tcpHandler"),
    localAddress = new InetSocketAddress("127.0.0.1", config.getInt("TCP_PORT")))

}


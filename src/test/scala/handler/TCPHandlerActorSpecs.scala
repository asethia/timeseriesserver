package handler

import java.net.InetSocketAddress

import akka.actor.{ActorRef, ActorSystem}
import akka.io.Tcp.Bound
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
  * This is test cases for TCP Handler Actor
  * Created by Arun Sethia on 26/07/16.
  */
class TCPHandlerActorSpecs
  extends TestKit(ActorSystem("tCPHandlerActorSpecSys", ConfigFactory.load("test.timeserver")))
    with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {

  var handlerActor: Option[ActorRef] = None

  override def beforeAll: Unit = {
    handlerActor = Some(system.actorOf(TCPHandler.props, "tcpHandler"))
  }

  override def afterAll: Unit = {
    system.terminate()
  }

  "The TCP Handler " should {
    "log message connected " in {
      handlerActor.get ! (Bound(new InetSocketAddress("127.0.0.1", system.settings.config.getInt("TCP_PORT"))))

    }
  }
}

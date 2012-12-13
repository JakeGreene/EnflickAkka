package com.enflick.akkatalk.message

import akka.actor.ActorSystem
import akka.actor.Props
import akka.routing.RoundRobinRouter
import akka.actor.ActorRef

object Office {
  val system = ActorSystem("OfficeSystem")

  val bob = system.actorOf(Props[OfficeDrone], name="Bob")
  val mary = system.actorOf(Props[OfficeDrone], name="Mary")
  val sue = system.actorOf(Props[OfficeDrone], name="Sue")
  val routees = Vector[ActorRef](bob, mary, sue)
  // Create a router, sending messages to one of the OfficeDrones in a Round-Robin fashion.
  val workerRouter = system.actorOf(Props[OfficeDrone].withRouter(RoundRobinRouter(routees = routees)))
  
  /* We will send a bunch of messages to our office drones
   * There are three things to note here:
   * 1. Although we are sending the messages in sequence (All DoWork then All Report the All TakeBreak)
   *    we will not see these executed in sequence; remember that we have just built an asynchronous, 
   *    concurrent system
   * 2. Each Actor will consume the messages in the same order they were sent (DoWork then TakeBreak). 
   *    Actor A sending messages 1, 2, 3 to Actor B can ensure that messages are consumed in the order
   *    1, 2, 3 (assuming Actor B is using the default FIFO MailBox).
   * 3. The order of messages is retained only on a per-sender basis. That is to say, messages from different 
   *    senders can and will be interwoven e.g Messages to Mary from the router and her co-workers will be
   *    interleaved; just watch Mary taking breaks and receiving reports.  
   */
  def main(args: Array[String]): Unit = {
    for (i <- 0 until (routees.size * 2)) {
      workerRouter ! DoWork
    }
    
    for (actor <- routees) {
      if (!actor.equals(mary)) {
        actor tell Report(mary) // "tell" and "!" are synonymous  
      }
    }
    
    for (i <- 0 until (routees.size * 2)) {
      workerRouter ! TakeBreak("just five minutes")
    }
    system.shutdown()
  }
}
package com.enflick.akkatalk.receive

import akka.actor.ActorSystem
import akka.actor.Props

object MyMain {
  
  // Create the environment for our Actor
  val system = ActorSystem("MyActorSystem")
  
  // Create our actor
  val actor = system.actorOf(Props[MyActor])
  
  def main(args: Array[String]): Unit = {
    actor ! "So many words"
    actor ! 42
    actor ! new AnyRef
    actor ! PoisonPill("Good Bye")
    system.shutdown()
  }

}
package com.enflick.akkatalk

import akka.actor.ActorSystem
import akka.actor.Props

/*
 * This example was stolen directly from Derek Wyatt's
 * book "Akka Concurrency"
 */
object BadShakespeareanMain {

  // Create the environment for our Actor to live in
  val system = ActorSystem("BadShakspearean")
  
  // Create a single bad Actor (one is enough)
  val actor = system.actorOf(Props[BadShakespeareanActor])

  // Send a message to our actor friend
  def send(msg: String) {
    println("Me: " + msg)
    actor ! msg
    Thread.sleep(100)
  }

  def main(args: Array[String]): Unit = {
    send("Good Morning");
    send("You're Terrible")
    system.shutdown()
  }

}
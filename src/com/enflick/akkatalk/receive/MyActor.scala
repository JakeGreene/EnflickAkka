package com.enflick.akkatalk.receive

import akka.actor.Actor

case class PoisonPill(message: String)

class MyActor extends Actor {
  def receive = {
    case s: String => 
      println("I can words: "+ s)
    case 42 => 
      println("I can hear numbers too")
    case PoisonPill(m) => 
      println("Time to die: "+ m)
    case a: Any => 
      println("I can hear pretty much anything: "+ a)
  }
}
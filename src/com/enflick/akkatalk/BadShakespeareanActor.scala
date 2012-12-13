package com.enflick.akkatalk

import akka.actor.Actor
import scala.collection.immutable.Stack

/*
 * Example stolen from Akka Concurrency by Derek Wyatt
 */
class BadShakespeareanActor extends Actor {
  def receive = {
    case "Good Morning" => println("Him: Forsooth 'tis the 'morn, but mourneth for thou doest I do!")
    case "You're Terrible" => println("Him: I know :(")
  }
}
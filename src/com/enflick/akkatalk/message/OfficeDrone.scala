package com.enflick.akkatalk.message

import akka.actor.Actor
import akka.actor.ActorRef


case class TakeBreak(time: String)
case object DoWork
case class Report(supervisor: ActorRef)
case class ReceiveReport(message: String)
case object Thanks

class OfficeDrone extends Actor {
  
  def receive = {
    case TakeBreak(breakLength) => 
      println(self.path.name +": Taking a break for "+breakLength)
    case DoWork => 
      println(self.path.name +": Doing Work")
    case Report(master) => 
      println(self.path.name +": Sending TPS report to: "+ master.path.name)
      master ! ReceiveReport("TPS Report from "+ self.path.name)
    case ReceiveReport(message) => 
      println(self.path.name +": Throwing out report: "+message)
      /* Send a message to the sender of this message. 'sender' is populated
       * with the ActorRef of the sending Actor just before 'receive' is called.
       */
      sender ! Thanks
    case Thanks =>
      println(self.path.name +": Received thanks from "+sender.path.name)
  }

}
package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import models.ChatBuddy
import models.ChatMessage

class ChatActor(out: ActorRef, manager: ActorRef) extends Actor {

  manager ! ChatBuddy(self)

  def receive = {
    case s: String => manager ! ChatMessage(s)
    case ChatMessage(msg) => out ! msg
    case m => println(s"Unhandled data from ChatActor $m")
  }
}

object ChatActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new ChatActor(out, manager))
}

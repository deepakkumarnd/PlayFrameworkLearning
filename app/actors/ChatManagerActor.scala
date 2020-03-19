package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import models.ChatBuddy
import models.ChatMessage

class ChatManagerActor extends Actor {
  private var buddies = List.empty[ActorRef]

  def receive = {
    case ChatBuddy(buddy) => buddies ::= buddy
    case msg: ChatMessage => for ( b <- buddies ) b ! msg
    case m => println(s"Unknown message ChatManager: ${m}")
  }
}

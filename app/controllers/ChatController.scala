package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import akka.actor.ActorRef
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import actors.ChatActor
import akka.actor.Props
import actors.ChatManagerActor

@Singleton
class ChatController @Inject() (cc: ControllerComponents)(
    implicit system: ActorSystem,
    mat: Materializer
) extends AbstractController(cc) {

  val manager = system.actorOf(Props[ChatManagerActor], "Manager")

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.chats.index(wsUrl))
  }

  def ws = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => ChatActor.props(out, manager))
  }

  private def wsUrl(implicit request: Request[AnyContent]): String =
    routes.ChatController.ws.absoluteURL().replaceAll("http://", "ws://")
}

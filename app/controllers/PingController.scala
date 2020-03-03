package controllers

import javax.inject._
import play.api._
import play.api.mvc._


@Singleton
class PingController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  // Send a static text response
  def ping = Action { implicit request: Request[AnyContent] =>
    Ok("PONG")
  }
}
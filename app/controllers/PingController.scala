package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class PingController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  // Send a static text response
  def ping = Action { implicit request: Request[AnyContent] =>
    Ok("PONG")
  }
}
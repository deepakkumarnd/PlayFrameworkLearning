package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class RegistrationController @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {
  def start() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.registrations.start())
  }
}

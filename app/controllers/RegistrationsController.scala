package controllers

import formdata.RegisterForm
import javax.inject._
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

@Singleton
class RegistrationController @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {

  private val form = Form(mapping(
    "name" -> nonEmptyText(6, 30),
    "email" -> email,
    "password" -> nonEmptyText(6, 20),
    "password_confirmation" -> nonEmptyText(6,20)
  )(RegisterForm.apply)(RegisterForm.unapply))

  def start() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.registrations.start(form))
  }

  def create() = Action { implicit request: Request[AnyContent] =>
    ???
  }
}

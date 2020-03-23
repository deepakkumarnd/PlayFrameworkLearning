package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import formdata.RegistrationForm
// Using forms and validations

@Singleton
class RegistrationsController @Inject()(val cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport{

  val form = Form(
      mapping(
        "name" -> nonEmptyText(3, 30),
        "email" -> email,
        "password" -> nonEmptyText(6, 30),
        "password-confirmation" -> nonEmptyText(6, 30),
        "gender" -> text,
        "age" -> number(18, 120)
      )(RegistrationForm.apply)(RegistrationForm.unapply)
    )

  def start() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.registrations.start(form))
  }

  def create() = Action { implicit request: Request[AnyContent] =>
    form.bindFromRequest.fold(
      formWithError => {
        BadRequest(views.html.registrations.start(formWithError))
      },
      data => {
        println("%%%%%%%%%%%%%%%%%%")
        println(data)
        Redirect(routes.RegistrationsController.show)
      }
    )

    Ok("ok")
  }

  def show() = Action { implicit request: Request[AnyContent] =>
    Ok("Success")
  }

}

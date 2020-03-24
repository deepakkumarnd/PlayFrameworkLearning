package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import formdata.RegistrationForm
import models.User
// Using forms and validations

@Singleton
class FormsController @Inject()(val cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport{

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
    Ok(views.html.forms.start(form))
  }

  def create() = Action { implicit request: Request[AnyContent] =>
    form.bindFromRequest.fold(
      formWithError => {
        BadRequest(views.html.forms.start(formWithError))
      },
      formData => {
        val user = buildUser(formData)
        println(user)
        Redirect(routes.FormsController.show())
      }
    )
  }

  def show() = Action { implicit request: Request[AnyContent] =>
    Ok("Success")
  }

  private def buildUser(formData: RegistrationForm): User = {
    User(
      formData.email,
      "",
      formData.name,
      (if (formData.gender == "male") 'M' else 'F'),
      formData.age
    )
  }

}

package controllers

import java.util.UUID

import formdata.RegisterForm
import javax.inject._
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import models.Registration

@Singleton
class RegistrationsController @Inject()(messageAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {

  private val form = Form(mapping(
    "name" -> nonEmptyText(6, 30),
    "email" -> email,
    "password" -> nonEmptyText(6, 20),
    "password_confirmation" -> nonEmptyText(6, 20)
  )(RegisterForm.apply)(RegisterForm.unapply))

  def start() = messageAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.registrations.start(form))
  }

  def create() = messageAction { implicit request: MessagesRequest[AnyContent] =>
    form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registrations.start(formWithErrors))
      },
      formData    => {
        val reg = createRegistration(formData)
        Ok("Success request " + reg.id)
      }
    )
  }

  private def createSalt(value: String): String = "salt"

  private def createRegistration(formData: RegisterForm): Registration = {
    Registration(UUID.randomUUID(), formData.name, formData.email, createSalt(formData.password))
  }
}

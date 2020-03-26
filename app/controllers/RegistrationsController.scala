package controllers

import java.util.UUID

import formdata.RegisterForm
import javax.inject._
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import models.Registration
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import dao.RegistrationDao


@Singleton
class RegistrationsController @Inject()(regDao: RegistrationDao, messageAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {

  // Constraint to validate a single field
  private val emailConstraint: Constraint[String] = Constraint({ email =>
    val inUse = regDao.emailAlreadyUsed(email)
    if (inUse) Invalid(Seq(ValidationError("Email already taken"))) else Valid
  })

  // Custom validation especially when we need to use multiple fields of the data model.
  def validate(password: String, passwordConfirmation: String): Boolean = password == passwordConfirmation

  private val form = Form(mapping(
    "name" -> nonEmptyText(6, 30),
    "email" -> email.verifying(emailConstraint),
    "password" -> nonEmptyText(6, 20),
    "password_confirmation" -> nonEmptyText(6, 20)
  )(RegisterForm.apply)(RegisterForm.unapply).verifying("Password & Confirmation does not match", fields =>
    fields match {
      case formData => validate(formData.password, formData.passwordConfirmation)
    }
  ))

  def start() = messageAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.registrations.start(form))
  }

  def create() = messageAction { implicit request: MessagesRequest[AnyContent] =>
    form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registrations.start(formWithErrors))
      },
      formData    => {
        val reg = buildRegistration(formData)
        regDao.save(reg)
        Ok("Success request " + reg.id)
      }
    )
  }

  private def createSalt(value: String): String = "salt"

  private def buildRegistration(formData: RegisterForm): Registration = {
    Registration(UUID.randomUUID(), formData.name, formData.email, createSalt(formData.password))
  }
}

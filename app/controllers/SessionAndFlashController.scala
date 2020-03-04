package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.util.Calendar

@Singleton
class SessionAndFlashController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  // If you have to keep data across multiple HTTP requests, you can save them in the Session or the Flash scope.
  // Data stored in the Session are available during the whole user session, and data stored in the flash scope are
  // only available to the next request. It’s important to understand that Session and Flash data are not stored in
  // the server but are added to each subsequent HTTP Request, using HTTP cookies.

  def createSession = Action { implicit request: Request[AnyContent] =>
    Ok("Check your session data in the cookie PLAY_SESSION").withSession("userName" -> "harry")
  }

  def readSession = Action { implicit request: Request[AnyContent] =>
    request.session
      .get("userName")
      .map { username => Ok(s"Welcome ${username}") }
      .getOrElse {
        Unauthorized("Oops you are not connected")
      }
  }

  def addSession = Action { implicit request: Request[AnyContent] =>
    Ok("Check the updated session in the cookie PLAY_SESSION")
      .withSession(request.session + ("lastAccessedAt" -> Calendar.getInstance().getTime().toString))
  }

  def removeSession = Action { implicit request: Request[AnyContent] =>
    Ok("Check the updated session in the cookie PLAY_SESSION").withSession(request.session - "lastAccessedAt")
  }

  def clearSession = Action { implicit request: Request[AnyContent] =>
    Ok("Check the updated session in the cookie PLAY_SESSION").withNewSession
  }

  def showFlash = Action { implicit request: Request[AnyContent] =>
    request.flash
      .get("alert")
      .map { message =>
        Ok(views.html.flash())
      }.getOrElse {
      Redirect(routes.SessionAndFlashController.showFlash()).flashing("alert" -> "Corona alert")
    }
  }
}

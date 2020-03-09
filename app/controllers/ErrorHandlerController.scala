package controllers

import javax.inject._
import play.api._
import play.api.mvc._

// The interface through which Play handles these errors is HttpErrorHandler. It defines two methods, onClientError, and onServerError.
// By default, Play returns errors in a HTML format. For a JSON API, it’s more consistent to return errors in JSON.
// play.http.errorHandler = play.api.http.JsonHttpErrorHandler OR
// More generic one: play.http.errorHandler = play.api.http.HtmlOrJsonHttpErrorHandler

@Singleton
class ErrorHandlerControll @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("Please read the comments of this action")
  }
}

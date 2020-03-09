package controllers

import javax.inject._
import play.api._
import play.api.mvc._

// Content negotiation is a mechanism that makes it possible to serve different representation of a same resource (URI).
// It is useful e.g. for writing Web Services supporting several output formats (XML, JSON, etc.).
// Server-driven negotiation is essentially performed using the Accept* requests headers.


// curl -XGET 'http://localhost:9000/content-negotiation/get-content' -H 'Accept: application/json'
// curl -XGET 'http://localhost:9000/content-negotiation/get-content'

// See the API documentation of the play.api.mvc.AcceptExtractors.Accepts object for the list of the MIME types
// supported by Play out of the box in the render method.

@Singleton
class ContentNegotiationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    render {
      case Accepts.Html() => Ok(views.html.index())
      case Accepts.Json() => Ok("{ \"ok\": \"Json response\"}")
    }
  }
}

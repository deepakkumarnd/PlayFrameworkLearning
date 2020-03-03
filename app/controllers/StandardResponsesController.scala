package controllers

import javax.inject._
import play.api._
import play.api.mvc._

// To send any response the response can be prepared as follows
//  import play.api.http.HttpEntity
//
//  def index = Action {
//    Result(
//      header = ResponseHeader(200, Map.empty),
//      body = HttpEntity.Strict(ByteString("Hello world!"), Some("text/plain"))
//    )
//  }
//
// Since this includes a lot of boiler plate code there are many helpers defined to
// make this easier. All of these helpers can be found in the play.api.mvc.Results trait and companion object.
// Here we are sending the following standard HTTP responses using the helpers
// send standard responses such as OK, NotFound, BadRequest, InternalServerError etc

@Singleton
class StandardResponsesController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  // Send a static text response
  def okResponse = Action { implicit request: Request[AnyContent] =>
    Ok("200 OK")
  }

  def notFound = Action { implicit request: Request[AnyContent] =>
    // NotFound
    NotFound("<h1>404 Sorry! The page that you have requested does not exists<h1>")
  }

  def badRequest = Action { implicit  request: Request[AnyContent] =>
    BadRequest("400 Bad Request")
  }

  def internalServerError = Action { implicit request: Request[AnyContent] =>
    InternalServerError("500 Oops something went wrong")
  }

  def custoemStatusResponse = Action { implicit request: Request[AnyContent] =>
    Status(420) ("420 Thats strange, Did you try something weired ?")
  }
}
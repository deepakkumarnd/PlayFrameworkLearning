package controllers

import javax.inject._
import play.api._
import play.api.libs.json.JsValue
import play.api.mvc._
import java.io.File


@Singleton
class BodyParserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  // JSON content coming in with Content-Type set to "application/json"
  def jsonContent = Action { request: Request[AnyContent] =>
    val jsonBody: Option[JsValue] = request.body.asJson

    jsonBody
      .map { json =>
        Ok("Got: " + (json \ "name").as[String])
      }.getOrElse {
        BadRequest("Expecting json content with Content-Type header set to application/json")
      }
  }

  def formData = Action { implicit request: Request[AnyContent] =>
    val formData = request.body.asFormUrlEncoded
    val name = formData.get("name").head
    Ok(s"You have submitted ${name} in the form input")
  }

  // Handling an XML content
  def xmlData = Action { implicit request: Request[AnyContent] =>
    val xmlData = request.body.asXml
    Ok(xmlData.get(0))
  }

  // Hnadling multipart form data
  def multipartForm = Action { implicit request: Request[AnyContent] =>
    // val formData = request.body.asMultipartFormData
    Ok("I am not sure how to do this")
  }

  def explicitJsonParser = Action(parse.json) { implicit request: Request[JsValue] =>
    Ok("We got some json content: " + (request.body \ "name").as[String])
  }

  //  Uploading a file
  private val parseFileInput = parse.using { request =>
    request.session
      .get("username")
      .map { name =>
        parse.file(new File("/tmp/" + name + ".upload"))
      }.getOrElse {
      sys.error("You are not logged to to do that")
    }
  }

  def uploadFile = Action(parseFileInput) { implicit request =>
    Ok("Saving the uploaded file")
  }
}

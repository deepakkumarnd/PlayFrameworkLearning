package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.util.Calendar

@Singleton
class ResponseController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def textResponse = Action { implicit request: Request[AnyContent] =>

    Ok("This is a text response with content type text/plain" )
  }

  def xmlResponse = Action { implicit request: Request[AnyContent] =>
    Ok(<message>This is a xml response with content type text/xml</message>)
  }

  // The benefit of using HTML instead of the "text/html" is that the charset will be
  // automatically handled for you and the actual Content-Type header will be set to text/html; charset=utf-8
  def htmlResponse = Action { implicit request: Request[AnyContent] =>
    Ok(<p>{now} | This is an html response with content type text/html</p>).as(HTML)
    // OR
    // Ok(<p>This is an html response with content type text/html</p>).as("text/html")
  }


  // Setting the CACHE_CONTROL and ETAG
  def httpHeaders = Action { implicit request: Request[AnyContent] =>
    Ok(<p>{now} | HTML response: Headers CACHE_CONTROL and ETAG are set. You will see the updated content after 10 seconds.</p>)
      .as(HTML).withHeaders(CACHE_CONTROL -> "max-age=10", ETAG -> "xxx")
  }

  // Note: It is ok to avoid the "implicit request: Request[AnyContent] =>" section if there is no view rendering
  // Verify this by going to inspect -> storage in google chrome
  def setCookie = Action {
    Ok("The response is sent with cookies").withCookies(Cookie("color", "blue"))
  }

  def unsetCookie = Action {
    Ok("Removing the cookie set using /set-cookie api").discardingCookies(DiscardingCookie("color"))
  }

  // Changing charset of a text based response
  // Here this works by the implicit magic, the implicit variable in scope is sent to the HTML function and that
  // is a type of Codec, so the charset of the current implicit variable will be used.
  def charset = Action { implicit request: Request[AnyContent] =>
    implicit val myCustomCharset = Codec.javaSupported("iso-8859-1")
    Ok(<h1>Using charset iso-8859-1</h1>).as(HTML)
  }

  // Note:  Missing part => RangeResult

  private

  def now: java.util.Date = Calendar.getInstance().getTime()
}

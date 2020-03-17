package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class TwirlTemplatingController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  // Organising code into subfolders & passing arguments is as follows
  def index() = Action { implicit request: Request[AnyContent] =>
    val tasks = List("one", "two", "three")
    
    Ok(views.html.tasks.index(tasks))
  }

  // Making use of `@` and interpolation
  def show() =  Action { implicit request: Request[AnyContent] =>
    val task = Map("name" -> "Make Coffee", "desc" -> "Here is how you make a coffee")
    Ok(views.html.tasks.show(task))
  }
}

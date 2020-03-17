package controllers

import dao.PostDao
import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class PostsController @Inject()(val post: PostDao, val controllerComponents: ControllerComponents) extends BaseController {
  def index() = Action { implicit request: Request[AnyContent] =>
    val data = post.all
    Ok(views.html.posts.index(data))
  }
}

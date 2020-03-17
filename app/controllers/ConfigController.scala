package controllers

import config.CassandraConfig
import javax.inject._
import play.api._
import play.api.mvc._

// Configuration is specified in application.conf

@Singleton
class ConfigController @Inject()(val config: Configuration, val controllerComponents: ControllerComponents) extends BaseController {
  def index() = Action { implicit request: Request[AnyContent] =>

    val xml = <config>
      <name>{config.get[String]("name")}</name>
      <purpose>{config.get[String]("purpose")}</purpose>
      <databaseConfig>{config.get[String]("databaseConfig")}</databaseConfig>
    </config>

    Ok("xml")
  }

  def db() = Action { implicit request: Request[AnyContent] =>
    val dbConfig = config.get[CassandraConfig]("databaseConfig")
    val xml = <config>
      <username>{dbConfig.username}</username>
    </config>
    Ok(xml)
  }
}

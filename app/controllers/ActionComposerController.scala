package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import builders.LoggingActionBuilder


// https://www.playframework.com/documentation/2.8.x/ScalaActionsComposition

@Singleton
class ActionComposerController @Inject()(loggingAction: LoggingActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {

  def index() = loggingAction {
    Ok("Using loggingActionBuilder to build this action")
  }
}

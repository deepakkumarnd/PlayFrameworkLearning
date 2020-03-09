package builders

import javax.inject.Inject
import play.api.mvc._
import play.api.Logging

import scala.concurrent.{ExecutionContext, Future}

class LoggingActionBuilder @Inject() (parser: BodyParsers.Default) (implicit ec: ExecutionContext)
    extends ActionBuilderImpl(parser) with Logging {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    logger.info("LoggingActionBuilder: Calling action")
    block(request)
  }
}

package auth.action

import play.api.mvc._
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class AuthAction @Inject()(cc: ControllerComponents, auth: Authentication)
  (implicit val executionContext: ExecutionContext) extends ActionBuilder[UserRequest, AnyContent] {

  val parser: BodyParser[AnyContent] = cc.parsers.default

  def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {

    auth.withAuth { authResult =>
      if (authResult.authOk) {
        block(new UserRequest(authResult.user, request))
      } else {
        Future.successful(Results.Unauthorized("Unauthorized access !!"))
      }
    }(request)
  }
}
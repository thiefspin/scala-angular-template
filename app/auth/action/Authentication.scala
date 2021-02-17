package auth.action

import auth.jwt.{JWT, JWTResult}
import models.User

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Request, Result}

import scala.concurrent.Future

@Singleton
class Authentication @Inject()() {

  private val KEY = "my-key"

  private lazy val authFailed = AuthResult(authOk = false, None)

  def withAuth[A](f: AuthResult => Future[Result])(implicit request: Request[A]): Future[Result] = {
    request.headers.get("jwt-token") match {
      case Some(token) => JWT.decode(token, Option(KEY)) match {
        case JWTResult.JWT(_, payload) => f(AuthResult(true, Json.fromJson[User](payload).asOpt))
        case _ => f(authFailed)
      }
      case None => f(authFailed)
    }
  }
}
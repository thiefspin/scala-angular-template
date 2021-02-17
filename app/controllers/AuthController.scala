package controllers

import auth.LoginRequest
import auth.jwt.{Algorithm, JWT}
import org.mindrot.jbcrypt.BCrypt
import play.api.libs.json.Json
import play.api.mvc._
import respositories.UserRepository

import javax.inject._

@Singleton
class AuthController @Inject()(cc: ControllerComponents, userRepo: UserRepository) extends AbstractController(cc) {

  def login(): Action[LoginRequest] = Action(parse.json[LoginRequest]) { implicit request =>
    userRepo.get(request.body.email) match {
      case Some(user) => {
        if (BCrypt.checkpw(request.body.password, user.password)) {
          val token = JWT.encode("my-key", Json.toJson(user), algorithm = Option(Algorithm.HS256))
          userRepo.updateLogin(user.id)
          Ok(Json.toJson(auth.Session(user, token)))
        } else {
          Unauthorized
        }
      }
      case None => NotFound
    }
  }
}

package controllers

import auth.LoginRequest
import auth.jwt.{Algorithm, JWT}
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class AuthController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def login(): Action[LoginRequest] = Action(parse.json[LoginRequest]) { implicit request =>
    val token = JWT.encode("my-key", Json.toJson(request.body), algorithm =  Option(Algorithm.HS256))
    print(JWT.decode(token, Option("my-key")))
    Ok(Json.obj("token" -> token))
  }
}

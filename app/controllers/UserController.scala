package controllers

import auth.action.AuthAction
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import respositories.UserRepository
import utils.controller.CustomAbstractController

import javax.inject.Inject

class UserController @Inject()(cc: ControllerComponents, repo: UserRepository, auth: AuthAction) extends CustomAbstractController(cc) {

  def list(): Action[AnyContent] = auth { _ =>
    Ok(Json.toJson(repo.list()))
  }

  def get(id: Long): Action[AnyContent] = auth { _ =>
    respond(repo.get(id))
  }

}

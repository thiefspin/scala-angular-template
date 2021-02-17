package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class HealthController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def health(): Action[AnyContent] = Action { _ =>
    Ok("Service is responding")
  }

}

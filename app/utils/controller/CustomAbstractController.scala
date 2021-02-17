package utils.controller

import play.api.libs.json.{Format, Json}
import play.api.mvc.{AbstractController, ControllerComponents, Result}

class CustomAbstractController(cc: ControllerComponents) extends AbstractController(cc) {

  def respond[A](result: Option[A])(implicit format: Format[A]): Result = {
    result match {
      case Some(value) => Ok(Json.toJson(value))
      case None => NotFound
    }
  }

}

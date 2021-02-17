package auth.action

import models.User
import play.api.mvc.{Request, WrappedRequest}

class UserRequest[A](
  val user: Option[User],
  val request: Request[A]
) extends WrappedRequest[A](request)
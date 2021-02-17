package auth.action

import models.User

case class AuthResult(
  authOk: Boolean,
  user: Option[User]
)

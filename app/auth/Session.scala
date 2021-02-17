package auth

import models.User
import play.api.libs.json.{Json, OFormat}

final case class Session(
  user: User,
  token: String
)

object Session {
  implicit val format: OFormat[Session] = Json.format[Session]
}
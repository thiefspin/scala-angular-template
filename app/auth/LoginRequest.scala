package auth

import play.api.libs.json.{Json, OFormat}

final case class LoginRequest(
  email: String,
  password: String
)

object LoginRequest {
  implicit val format: OFormat[LoginRequest] = Json.format[LoginRequest]
}

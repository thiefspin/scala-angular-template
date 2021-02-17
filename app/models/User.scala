package models

import anorm.{Macro, RowParser}
import org.joda.time.DateTime
import play.api.libs.json.{Json, OFormat}
import utils.json.DateTimeParser._

final case class User(
  id: Long,
  email: String,
  name: String,
  surname: String,
  password: String,
  created: DateTime,
  lastLogin: Option[DateTime]
)

object User {
  implicit val format: OFormat[User] = Json.format[User]
  implicit val parser: RowParser[User] = Macro.indexedParser[User]
}
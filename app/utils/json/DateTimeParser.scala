package utils.json

import org.joda.time.DateTime
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

object DateTimeParser {

  implicit val dateFormatter: Format[DateTime] = new Format[DateTime] {
    override def reads(json: JsValue): JsResult[DateTime] = json match {
      case JsString(value) => Try {
        new DateTime(value)
      } match {
        case Success(value) => JsSuccess(value)
        case Failure(exception) => JsError(s"Could not parse date time value: ${exception.getMessage}")
      }
      case JsNumber(value) => Try {
        new DateTime(value.toLong)
      } match {
        case Success(value) => JsSuccess(value)
        case Failure(exception) => JsError(s"Could not parse date time value: ${exception.getMessage}")
      }
      case _ => JsError("Invalid type, expected a datetime for timestamp")
    }

    override def writes(o: DateTime): JsValue = JsString(o.toString)
  }
}
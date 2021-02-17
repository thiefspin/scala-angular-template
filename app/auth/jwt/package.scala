package auth

import play.api.libs.json._

/** Provides classes and objects that define JWT Types
 */
package object jwt {

  sealed trait Algorithm{
    def name:String
  }

  /**
   * Algorithm represent type of algorithms that used on JWT
   */
  object Algorithm {

    /**
     * Represent type of HMAC Algorithm that using SHA-256 hash algorithm
     */
    case object HS256 extends Algorithm {
      override def toString = "HmacSHA256"
      def name = "HS256"
    }
    /**
     * Represent type of HMAC Algorithm that using SHA-384 hash algorithm
     */
    case object HS384 extends Algorithm {
      override def toString = "HmacSHA384"
      def name = "HS384"
    }
    /**
     * Represent type of HMAC Algorithm that using SHA-512 hash algorithm
     */
    case object HS512 extends Algorithm {
      override def toString = "HmacSHA512"
      def name = "HS512"
    }
    /**
     * Represent type of RSASSA Algorithm that using SHA-256 hash algorithm
     */
    case object NONE extends Algorithm {
      override def toString = "none"
      def name = "none"
    }
    /**
     * Represent type of RSASSA Algorithm that using SHA-256 hash algorithm
     */
    case object RS256 extends Algorithm {
      override def toString = "SHA256withRSA"
      def name = "RS256"
    }
    /**
     * Represent type of RSASSA Algorithm that using SHA-384 hash algorithm
     */
    case object RS384 extends Algorithm {
      override def toString = "SHA384withRSA"
      def name = "RS384"
    }
    /**
     * Represent type of RSASSA Algorithm that using SHA-512 hash algorithm
     */
    case object RS512 extends Algorithm {
      override def toString = "SHA512withRSA"
      def name = "RS512"
    }
  }

  /**
   * Represent Reads and Writes for Algorithm
   */
  implicit object AlgorithmFmt extends Format[Algorithm] {
    import Algorithm._

    def reads(v: JsValue): JsResult[Algorithm] = v match {
      //HSxxx algorithms
      case JsString("HmacSHA256") => JsSuccess(HS256)
      case JsString("HmacSHA384") => JsSuccess(HS384)
      case JsString("HmacSHA512") => JsSuccess(HS512)
      // Alias names
      case JsString("HS256") => JsSuccess(HS256)
      case JsString("HS384") => JsSuccess(HS384)
      case JsString("HS512") => JsSuccess(HS512)

      //RSxxx algorithms
      case JsString("SHA256withRSA") => JsSuccess(RS256)
      case JsString("SHA384withRSA") => JsSuccess(RS384)
      case JsString("SHA512withRSA") => JsSuccess(RS512)
      // Alias names
      case JsString("RS256") => JsSuccess(RS256)
      case JsString("RS384") => JsSuccess(RS384)
      case JsString("RS512") => JsSuccess(RS512)

      case JsString("none") => JsSuccess(NONE)
      case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.unsupported.algorithm"))))
    }

    def writes(alg: Algorithm): JsValue = JsString(alg.name)
  }

  /**
   * Represent JWT Header
   * @param alg is algorithm that used to encrypt token
   * @param extraHeader is represent
   */
  case class JWTHeader(alg: Algorithm, extraHeader: JsObject = Json.obj()){
    val `type` = "JWT"

    def toJson = Json.obj("typ" -> `type`, "alg" -> alg) ++ extraHeader
  }

  /**
   * Represent result from decode operation
   */
  sealed trait JWTResult
  object JWTResult {

    /**
     * Represent JWT data
     * @param header is the header for jwt
     * @param payload is the data for jwt
     */
    case class JWT(header: JWTHeader, payload: JsObject) extends JWTResult

    /**
     * Represent Failure Result from decode operation in case JWT has too many segments
     */
    case object TooManySegments extends JWTResult

    /**
     * Represent Failure Result from decode operation in case JWT has not enough segments
     */
    case object NotEnoughSegments extends JWTResult

    /**
     * Represent Failure Result from decode operation in case JWT is empty
     */
    case object EmptyJWT extends JWTResult

    /**
     * Represent Failure Result from decode operation in case JWT has invalid Signature
     */
    case object InvalidSignature extends JWTResult

    /**
     * Represent Failure Result from decode operation in case JWT has invalid header
     */
    case object InvalidHeader extends JWTResult
  }

}

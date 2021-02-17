package respositories

import anorm.SqlStringInterpolation
import models.User

import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime
import play.api.db.Database
import utils.anorm.AnormExtension._

@Singleton
class UserRepository @Inject()(db: Database) {

  def get(email: String): Option[User] = {
    db.withConnection { implicit c =>
      SQL"""
         SELECT * FROM
         platform.users
         WHERE email = $email
       """.as(User.parser.singleOpt)
    }
  }

  def get(id: Long): Option[User] = {
    db.withConnection { implicit c =>
      SQL"""
         SELECT * FROM
         platform.users
         WHERE id = $id
       """.as(User.parser.singleOpt)
    }
  }

  def list(): List[User] = {
    db.withConnection {implicit c =>
      SQL"""
         SELECT * FROM
         platform.users
       """.as(User.parser.*)
    }
  }

  def updateLogin(id: Long): Unit = {
    db.withConnection {implicit c =>
      SQL"""
         UPDATE
         platform.users
         SET last_login = ${DateTime.now()}
         WHERE id = $id
       """.executeUpdate()
    }
  }

}
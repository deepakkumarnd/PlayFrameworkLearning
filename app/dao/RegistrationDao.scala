package dao

import javax.inject.{Inject, Singleton}
import models.{Registration, RegistrationByEmail}
import play.api.Configuration

@Singleton
class RegistrationDao @Inject() (config: Configuration) extends BaseDao(config) {

  lazy val findEmailQuery =
    """
      |SELECT * FROM
      |registrations_by_email
      |WHERE
      |email = ?
      |LIMIT 1
    """.stripMargin

  lazy val findEmailStmt = db.prepare(findEmailQuery)

  lazy val insertEmail =
    """
      |INSERT INTO
      |registrations_by_email
      |(id, email)
      |VALUES(?, ?)
    """.stripMargin

  lazy val insertEmailStmt = db.prepare(insertEmail)

  lazy val insertRowQuery =
    """
      |INSERT INTO
      |registrations
      |(id, name, email, password_salt)
      |VALUES(?, ?, ?, ?)
    """.stripMargin

  lazy val insertRowStmt = db.prepare(insertRowQuery)


  def emailAlreadyUsed(email: String): Boolean = {
    Option(db.execute(findEmailStmt.bind(email)).one()) match {
      case None => false
      case Some(row) =>
        val rec = RegistrationByEmail(row.getString("email"), Option(row.getUuid(1)))
        rec.id match {
          case None => false
          case _    => true
        }
    }
  }

  def save(reg: Registration): Option[Registration] = {
    db.execute(insertRowStmt.bind(reg.id, reg.name, reg.email, reg.passwordSalt))
    db.execute(insertEmailStmt.bind(reg.id, reg.email))

    Option(reg)
  }
}

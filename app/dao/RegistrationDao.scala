package dao

import javax.inject.{Inject, Singleton}
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

  def emailAlreadyUsed(email: String): Boolean = {
    val row = db.execute(findEmailStmt.bind(email)).one()
    !(row == null || row.isNull(1))
  }
}

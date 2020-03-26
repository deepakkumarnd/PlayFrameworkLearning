package models

import java.util.UUID

case class RegistrationByEmail(email: String, id: Option[UUID])

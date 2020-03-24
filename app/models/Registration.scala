package models

import java.util.UUID

case class Registration(id: UUID,
   name: String,
   email: String,
   passwordSalt: String)

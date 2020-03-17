package models

import java.sql.Timestamp
import java.util.UUID

case class Post(id: UUID, title: String, content: String)

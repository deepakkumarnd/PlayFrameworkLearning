package models

case class User(
  email: String,
  passwordSalt: String,
  name: String,
  gender: Boolean,
  age: Int
)
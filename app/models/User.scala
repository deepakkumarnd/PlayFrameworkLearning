package models

case class User(
  email: String,
  passwordSalt: String,
  name: String,
  gender: Char,
  age: Int
)
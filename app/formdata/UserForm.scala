package formdata

case class UserForm(
  name: String,
  email: String,
  password: String,
  passwordConfirmation: String,
  gender: String,
  age: Int
)
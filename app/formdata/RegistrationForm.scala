package formdata

case class RegistrationForm(
  name: String,
  email: String,
  password: String,
  passwordConfirmation: String,
  gender: String,
  age: Int
)
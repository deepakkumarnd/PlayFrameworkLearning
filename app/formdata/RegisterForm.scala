package formdata

case class RegisterForm(
  name: String,
  email: String,
  password: String,
  passwordConfirmation: String)

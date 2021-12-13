package org.booking.auth.user

private val emailRegex = "[\\w\\d]+@[\\w]+\\.[a-z]{2,6}\$".toRegex()

fun Email.isValid(): Boolean {
    return emailRegex.matches(this)
}

fun RegisterUser.isValid(): Boolean {
    return this.let {
        name.isNotBlank() && email.isValid()
    }
}

fun LoginUser.isValid(): Boolean {
    return this.let {
        password.isNotBlank() && email.isValid()
    }
}
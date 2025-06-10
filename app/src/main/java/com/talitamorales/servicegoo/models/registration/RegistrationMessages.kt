package com.talitamorales.servicegoo.models.registration

enum class RegistrationMessages(val message: String) {
    emptyFieldError("Please fill in all fields"),
    minimumPasswordLenghtError("The password must contain at least 8 characters")

}
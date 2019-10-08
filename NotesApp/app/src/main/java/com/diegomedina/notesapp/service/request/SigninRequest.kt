package com.diegomedina.notesapp.service.request

data class SigninRequest(
    val name: String,
    val email: String,
    val password: String
)

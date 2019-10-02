package com.diegomedina.notesapp.controller

import com.diegomedina.notesapp.service.AuthService
import com.diegomedina.notesapp.service.request.LoginRequest

class AuthController {
    private val authService = RetrofitController.retrofit.create(AuthService::class.java)

    suspend fun login(email: String, password: String) {
        val request = LoginRequest(email, password)
        val response = authService.login(request)

        RetrofitController.accessToken = response.authToken
    }

    suspend fun logout() {
        authService.logout()
        RetrofitController.accessToken = null
    }
}

package com.diegomedina.notesapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diegomedina.notesapp.controller.RetrofitController
import com.diegomedina.notesapp.view.auth.AuthActivity
import com.diegomedina.notesapp.view.home.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = when {
            RetrofitController.accessToken != null -> Intent(this, HomeActivity::class.java)
            else -> Intent(this, AuthActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}

package com.diegomedina.notesapp.view.auth

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.diegomedina.notesapp.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, LoginFragment(), LoginFragmentTag)
            .commit()

    }

    companion object {
        private const val LoginFragmentTag = "LoginFragmentTag"
    }
}

package com.diegomedina.notesapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.diegomedina.notesapp.R
import com.diegomedina.notesapp.controller.AuthController
import com.diegomedina.notesapp.view.home.HomeActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class LoginFragment : Fragment(), CoroutineScope {
    private val authController = AuthController()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener { login() }
//        signinButton.setOnClickListener{signin()}

    }

//    private fun signin() {
//        val signInIntent: Intent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//
//
//        setContentView(R.layout.activity_auth)
//    }

    private fun login() {
        val email = emailEditText.editableText.toString()
        val password = passwordEditText.editableText.toString()

        launch(Dispatchers.IO) {
            try {
                authController.login(email, password)
                withContext(Dispatchers.Main) {
                    activity?.let {
                        it.startActivity(Intent(it, HomeActivity::class.java))
                        it.finish()
                    }
                }
            } catch (exception: Exception) {

            }
        }
    }
}

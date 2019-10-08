package com.diegomedina.notesapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diegomedina.notesapp.R
import com.diegomedina.notesapp.controller.AuthController
import com.diegomedina.notesapp.view.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_sign.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SigninFragment : Fragment(), CoroutineScope {
    private val authController = AuthController()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_sign, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signButton.setOnClickListener { signin() }

    }


    private fun signin() {
        val name = nameEditText.editableText.toString()
        val email = emailEditText.editableText.toString()
        val password = passwordEditText.editableText.toString()

        launch(Dispatchers.IO) {
            try {
                authController.signin(name, email, password)
                withContext(Dispatchers.Main) {
                    activity?.let {
                        it.startActivity(Intent(it, LoginFragment::class.java))
                        it.finish()
                    }
                }
            } catch (exception: Exception) {

            }
        }
    }
}
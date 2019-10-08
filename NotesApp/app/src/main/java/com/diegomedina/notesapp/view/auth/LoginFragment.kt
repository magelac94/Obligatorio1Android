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
import com.diegomedina.notesapp.view.home.principal.PrincipalFragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext
import android.R.attr.fragment
import androidx.core.view.isVisible
import com.diegomedina.notesapp.helper.gone


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
        signinButton.setOnClickListener { signin() }

    }

    private fun signin() {
        //linear_login.setVisibility(View.INVISIBLE)  // chequear ya que el boton de atras no funciona y agregar un boton fisico para volver a atras
        linear_login.gone()
        showFragment(SigninFragment(), SigninFragmentTag)


    }


    private fun showFragment(fragment: Fragment, tag: String) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.container, fragment, tag)
            ?.commit()

        //activity.getfragfragment_login.setVisibility(View.GONE)
    }


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



    companion object {
        private const val SigninFragmentTag = "SigninFragmentTag"

    }

}





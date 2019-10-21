package com.diegomedina.notesapp.view.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diegomedina.notesapp.R
import com.diegomedina.notesapp.controller.AuthController
import com.diegomedina.notesapp.controller.NoteController
import com.diegomedina.notesapp.view.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ProfileFragment : Fragment(), CoroutineScope {
    private val authController = AuthController()
    private val controller = NoteController()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var contador_work = 0
        var contador_study = 0
        var contador_shopping = 0
        var contador_leisure = 0
        var contador_finance = 0
        var contador_home = 0
        var contador_completadas = 0

        var lista_datos = ArrayList<Int>()
        var lista_labels = ArrayList<String>()

        launch(Dispatchers.IO) {
            try {
                val notes = controller.getAllNotes()
                for (note in notes) {
                    if (note.category_id == 1)
                        contador_work++
                    if (note.category_id == 2)
                        contador_study++
                    if (note.category_id == 3)
                        contador_shopping++
                    if (note.category_id == 4)
                        contador_leisure++
                    if (note.category_id == 5)
                        contador_finance++
                    if (note.category_id == 6)
                        contador_home++
                    if (note.completed)
                        contador_completadas++
                }
                lista_datos.add(0)
                lista_datos.add(contador_work)
                lista_datos.add(contador_study)
                lista_datos.add(contador_shopping)
                lista_datos.add(contador_leisure)
                lista_datos.add(contador_finance)
                lista_datos.add(contador_home)

                var maximo = lista_datos.max()

                var nombre_cat_alta = ""
                if (maximo != 0) {
                    var num_categoria = lista_datos.indexOf(maximo)
                    if (num_categoria == 1)
                        nombre_cat_alta = "Work"
                    if (num_categoria == 2)
                        nombre_cat_alta = "Study"
                    if (num_categoria == 3)
                        nombre_cat_alta = "Shopping"
                    if (num_categoria == 4)
                        nombre_cat_alta = "Leisure"
                    if (num_categoria == 5)
                        nombre_cat_alta = "Finance"
                    if (num_categoria == 6)
                        nombre_cat_alta = "Home"
                }

                var contador_categorias = 0
                if (contador_work > 0)
                    contador_categorias++
                if (contador_study > 0)
                    contador_categorias++
                if (contador_shopping > 0)
                    contador_categorias ++
                if (contador_leisure > 0)
                    contador_categorias++
                if (contador_finance > 0 )
                    contador_categorias++
                if (contador_home > 0 )
                    contador_categorias++

                categ_used.setText(contador_categorias.toString())
                total_task.setText(notes.size.toString())
                completadas.setText(contador_completadas.toString())
                cat_mas_usada.setText(nombre_cat_alta)


//
            } catch (error: Exception) {
                if (error is HttpException) {
                    error.code()
                }
            }
        }

        logOut.setOnClickListener { logout() }

    }

    private fun logout() {
        launch(Dispatchers.IO) {
            try {
                authController.logout()
                withContext(Dispatchers.Main) {
                    activity?.let {
                        it.startActivity(Intent(it, AuthActivity::class.java))
                        it.finish()
                    }
                }
            } catch (error: Exception) {

            }
        }
    }
}

package com.diegomedina.notesapp.view.home.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegomedina.notesapp.R
import com.diegomedina.notesapp.controller.NoteController
import com.diegomedina.notesapp.helper.gone
import com.diegomedina.notesapp.helper.visible
import com.diegomedina.notesapp.view.auth.LoginFragment
import com.diegomedina.notesapp.view.auth.SigninFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class NotesFragment : Fragment(), CoroutineScope {
    private val adapter = NotesAdapter()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val controller = NoteController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_notes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        recyclerView.layoutManager = LinearLayoutManager(this)
        //        recyclerView.adapter = adapter

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@NotesFragment.adapter
        }

        getNotes()

        floatingActionButton.setOnClickListener { addNotes() }

    }

    private fun getNotes() {
        progressBar.visible()
        recyclerView.gone()

        launch(Dispatchers.IO) {
            try {
                val notes = controller.getAllNotes()
                withContext(Dispatchers.Main) {
                    adapter.notes = notes
                    if (notes.size == 0) {
                        texto_inicial.setText("You don\'t have any TODOs yet. \nPress the + button to create your first one")
                    } else {
                        texto_inicial.gone()
                    }

                    progressBar.gone()
                    recyclerView.visible()
                }
            } catch (error: Exception) {
                if (error is HttpException) {
                    error.code()
                }
            }
        }
    }


    private fun showFragment(fragment: Fragment, tag: String) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.container, fragment, tag)
            ?.commit()

        //activity.getfragfragment_login.setVisibility(View.GONE)
    }

    private fun addNotes() {
        //linear_login.setVisibility(View.INVISIBLE)  // chequear ya que el boton de atras no funciona y agregar un boton fisico para volver a atras
        relative_notes.gone()
        showFragment(AddNotesFragment(), NotesFragment.AddNotesFragmentTag)

    }


    companion object {
        private const val AddNotesFragmentTag = "AddNotesFragmentTag"

    }


}

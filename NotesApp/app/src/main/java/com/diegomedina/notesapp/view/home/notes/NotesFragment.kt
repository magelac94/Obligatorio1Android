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
            layoutManager = LinearLayoutManager(activity)
            adapter = this@NotesFragment.adapter
        }

        getNotes()
    }

    private fun getNotes() {
        progressBar.visible()
        recyclerView.gone()

        launch(Dispatchers.IO) {
            try {
                val notes = controller.getAllNotes()
                withContext(Dispatchers.Main) {
                    adapter.notes = notes

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
}

package com.diegomedina.notesapp.view.home.notes


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.diegomedina.notesapp.R
//import com.diegomedina.notesapp.controller.AuthController
import com.diegomedina.notesapp.controller.NoteController
import com.diegomedina.notesapp.extensions.textString
import kotlinx.android.synthetic.main.fragment_add_notes.*
import kotlinx.android.synthetic.main.view_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class AddNotesFragment : Fragment(), CoroutineScope {

    private val noteController = NoteController()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    lateinit var spinner_cat: Spinner
    lateinit var spinner_prio: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_notes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton.setOnClickListener { addNotes() }
    }

    fun addNotes() {

        var priority = "0"
        var completed = false
        var categoryid = 0


        // se verifica que description no este vacio
        if (input.textString().isEmpty()) {
            Toast.makeText(context, "You must enter a Description", Toast.LENGTH_SHORT).show()
            return
        }

        var description = input.editableText.toString()


        // se verifica que prioridad fue seleccionada
        radio_group_prio.setOnClickListener {
            if (r1.isChecked)
                priority = r1.toString()
            if (r2.isChecked)
                priority = r2.toString()
            if (r3.isChecked)
                priority = r3.toString()
        }

        var due_date = "2019-09-23T12:30:15.165Z"
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            due_date = "" + month + "/" + dayOfMonth + "/" + year
            println("FECHA DEL CALENDAR")
            println(due_date)
        }

        // se verifica categoria seleccionada fue seleccionada
        radio_group_cat.setOnClickListener {
            if (c1.isChecked)
                categoryid = 1
            if (c2.isChecked)
                categoryid = 2
            if (c3.isChecked)
                categoryid = 3
            if (c4.isChecked)
                categoryid = 4
            if (c5.isChecked)
                categoryid = 5
            if (c6.isChecked)
                categoryid = 6
        }

        launch(Dispatchers.IO) {
            try {
                noteController.addNote(priority, description, due_date, completed, categoryid)
                withContext(Dispatchers.Main) {
                    activity?.let {
                        it.startActivity(Intent(it, NotesFragment::class.java))
                        it.finish()
                    }
                }
            } catch (exception: Exception) {

            }
        }

    }
}

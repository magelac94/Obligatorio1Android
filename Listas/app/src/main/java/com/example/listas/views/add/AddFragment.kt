package com.example.listas.views.add

import android.content.Context
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.listas.R
import com.example.listas.extensions.textString
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.layout_todo_row.*

class AddFragment : Fragment() {

    lateinit var spinner_cat : Spinner
    lateinit var spinner_prio : Spinner

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(input: String, spinner_prio: String, color: String)
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButton.setOnClickListener {
            onAddButtonPressed()
        }

        // SPINNER DE LAS CATEGORIAS
        spinner_cat = view.findViewById(R.id.spinner_category) as Spinner

        val spinnercats = arrayOf("Work","Study","Shopping","Leisure")

        spinner_cat.adapter = ArrayAdapter<String>( view.context, android.R.layout.simple_list_item_1,spinnercats)
        spinner_cat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }

        // SPINNER DE LAS PRIORIDADES

        spinner_prio = view.findViewById(R.id.spinner_prio) as Spinner

        val spinnerprio = arrayOf("High Priority","Medium Priority", "Low Priority")

        spinner_prio.adapter = ArrayAdapter<String>( view.context, android.R.layout.simple_list_item_1,spinnerprio)
        spinner_prio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }
        }
    }

    private fun onAddButtonPressed() {
        if (input.textString().isEmpty()) {
            Toast.makeText(context, "You must enter a Task", Toast.LENGTH_SHORT).show()
            return
        }
        if (spinner_prio.selectedItem.toString().isEmpty()){
            Toast.makeText(context, "You must enter a Priority.", Toast.LENGTH_SHORT).show()
            return
        }
        if (spinner_cat.selectedItem.toString().isEmpty()){
            Toast.makeText(context, "You must enter a Category.", Toast.LENGTH_SHORT).show()
            return
        }

        listener?.onFragmentInteraction(input.textString(),spinner_prio.selectedItem.toString(),spinner_cat.selectedItem.toString())
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}

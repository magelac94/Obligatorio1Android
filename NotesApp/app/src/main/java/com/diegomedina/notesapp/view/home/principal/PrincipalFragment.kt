package com.diegomedina.notesapp.view.home.principal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegomedina.notesapp.R
import com.diegomedina.notesapp.controller.AuthController
import kotlinx.android.synthetic.main.fragment_principal.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import android.R.id
import android.graphics.Color
import com.diegomedina.notesapp.controller.NoteController
import com.diegomedina.notesapp.helper.gone
import com.diegomedina.notesapp.helper.visible
import com.diegomedina.notesapp.view.home.notes.NotesAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lecho.lib.hellocharts.view.PieChartView
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.model.PieChartData
import retrofit2.HttpException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class PrincipalFragment : Fragment(), CoroutineScope {

    private val controller = NoteController()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_principal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var contador_work = 0
        var contador_study = 0
        var contador_shopping = 0
        var contador_leisure = 0
        var contador_finance = 0
        var contador_home = 0

        var lista_datos = ArrayList<Int>()
        var lista_labels = ArrayList<String>()

        super.onViewCreated(view, savedInstanceState)

        launch(Dispatchers.IO) {
            try {
                val notes = controller.getAllNotes()
                for (note in notes) {
                    if (note.category_id == 1) {
                        contador_work++
                    }
                    if (note.category_id == 2) {
                        contador_study++
                    }
                    if (note.category_id == 3) {
                        contador_shopping++
                    }
                    if (note.category_id == 4) {
                        contador_leisure++
                    }
                    if (note.category_id == 5) {
                        contador_finance++
                    }
                    if (note.category_id == 6) {
                        contador_home++
                    }
                }
                    var total = notes.size

                lista_datos.add((contador_work*100)/total)
                lista_labels.add("Work")

                lista_datos.add((contador_study*100)/total)
                lista_labels.add("Study")

                lista_datos.add((contador_shopping*100)/total)
                lista_labels.add("Shopping")

                lista_datos.add((contador_leisure*100)/total)
                lista_labels.add("Leisure")

                lista_datos.add((contador_finance*100)/total)
                lista_labels.add("Finance")

                lista_datos.add((contador_home*100)/total)
                lista_labels.add("Home")

                crearGraficos(lista_datos,lista_labels)

            } catch (error: Exception) {
                if (error is HttpException) {
                    error.code()
                }
            }
        }
    }


    fun crearGraficos(lista_datos: ArrayList<Int>, lista_labels : ArrayList<String>){
        var lista_graficos = ArrayList<PieChartView>()
        lista_graficos.add(chart1)
        lista_graficos.add(chart2)
        lista_graficos.add(chart3)
        lista_graficos.add(chart4)

        for ((index,value) in lista_datos.withIndex()) {

            val entradas = ArrayList<SliceValue>()
            entradas.add(SliceValue(value.toFloat(), Color.GREEN).setLabel(""))
            entradas.add(SliceValue((100-value).toFloat(), Color.GRAY).setLabel(""))

            val grafico1 = PieChartData(entradas)

            grafico1.setHasCenterCircle(true).setCenterText1(value.toString()+"%").setCenterText1FontSize(20).setCenterText2(lista_labels[index]).setHasLabelsOutside(true)

            lista_graficos[index].setPieChartData(grafico1)
        }
    }
}
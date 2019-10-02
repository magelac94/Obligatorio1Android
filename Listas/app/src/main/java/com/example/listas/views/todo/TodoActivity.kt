package com.example.listas.views.todo


import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.listas.R
import com.example.listas.data.Action
import com.example.listas.views.add.AddActivity
import kotlinx.android.synthetic.main.activity_todo.*

class TodoActivity : AppCompatActivity() {

    private val addActionRequestCode = 1001

    private val actions = arrayListOf<Action>(
        //Action(0, "Example Action")
    )

    // Funcion para tachar el textview cuando checkbox se tilda
    fun onCheckboxClicked(view: View) {

        var txt = findViewById(R.id.todoAction) as TextView
        var prio = findViewById(R.id.priority) as TextView

        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox1 -> {
                    if (checked) {
                        txt.paintFlags = txt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        prio.paintFlags = prio.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        txt.paintFlags = txt.paintFlags xor Paint.STRIKE_THRU_TEXT_FLAG
                        prio.paintFlags = prio.paintFlags xor Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                TodoFragment.newInstance(actions), TodoFragment.todoFragmentTag
            )
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = item?.let {
        when (it.itemId) {
            R.id.action_add -> {
                startActivityForResult(
                    Intent(this, AddActivity::class.java)
                    , addActionRequestCode
                )
            }
        }
        true
    } ?: super.onOptionsItemSelected(item)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            addActionRequestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        addActionToTodoList(it.getStringExtra(resultInput), it.getStringExtra(
                            resultPriority), it.getStringExtra(resultColor))
                    }
                }
            }
        }
    }

    private fun addActionToTodoList(action: String, priority: String, color: String) {

        actions.add(Action(0, action, priority, color))

        // Escondo el texto inicial, haciendo invisible el textview
        val txt = findViewById(R.id.texto_inicial) as TextView
        txt.setVisibility(View.GONE)

        updateCurrentFragment()
    }

    private fun updateCurrentFragment() {
        supportFragmentManager.findFragmentByTag(TodoFragment.todoFragmentTag).also {
            it?.let { fragment ->
                if (fragment.isVisible && fragment is TodoFragment) {
                    fragment.updateAdapterData(actions)
                }
            }
        }
    }

    companion object {

        const val resultInput = "String:Input"
        const val resultPriority = "String:Priority"
        const val resultColor = "String:Color"
    }
}

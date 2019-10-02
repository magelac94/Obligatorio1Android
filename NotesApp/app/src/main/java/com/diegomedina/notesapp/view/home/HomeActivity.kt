package com.diegomedina.notesapp.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.diegomedina.notesapp.R
import com.diegomedina.notesapp.view.home.notes.NotesFragment
import com.diegomedina.notesapp.view.home.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        showFragment(NotesFragment(), NotesFragmentTag)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            removeActiveFragment()

            when (menuItem.itemId) {
                R.id.notes -> showFragment(NotesFragment(), NotesFragmentTag)
                R.id.profile -> showFragment(ProfileFragment(), ProfileFragmentTag)
            }

            true
        }
    }

    private fun removeActiveFragment() {
        listOf(NotesFragmentTag, ProfileFragmentTag).forEach { tag ->
            val fragment = supportFragmentManager.findFragmentByTag(tag)
            fragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .remove(it)
                    .commit()
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, tag)
            .commit()
    }

    companion object {
        private const val NotesFragmentTag = "NotesFragmentTag"
        private const val ProfileFragmentTag = "ProfileFragmentTag"
    }
}

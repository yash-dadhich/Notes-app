package com.charging.notesapp.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.charging.notesapp.NoteApplication
import com.charging.notesapp.R
import com.charging.notesapp.adapter.NoteAdapter
import com.charging.notesapp.viewmodel.NoteViewModel
import com.charging.notesapp.viewmodel.NoteViewModelFactory
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab:FloatingActionButton = findViewById(R.id.fab)
        val nightModeSwitch: SwitchCompat = findViewById(R.id.customSwitch)
        val chipView:Chip = findViewById(R.id.chipView)
        val recyclerView: RecyclerView = findViewById(R.id.notesRecycler)

        if (NoteApplication.isFirstTime){
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    nightModeSwitch.isChecked = true
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    nightModeSwitch.isChecked = false
                }
            }
            NoteApplication.isFirstTime = false
        }

        chipView.setOnCheckedChangeListener { _, isChecked ->
            chipView.animate().rotation(360f).setDuration(1000).start()
            Handler(Looper.getMainLooper()).postDelayed({
                if (isChecked) {
                    chipView.text = "grid"
                    chipView.setChipIconResource(R.drawable.ic_round_grid_on_24)
                    recyclerView.layoutManager =StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                }
                else{
                    chipView.text = "list"
                    chipView.setChipIconResource(R.drawable.ic_list)
                    recyclerView.layoutManager =StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
                }

            }, 500)
        }

        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

        val noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
        noteViewModel.myAllNotes.observe(this) { notes ->
            // update UI
            noteAdapter.setNote(notes)
        }

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity,NoteAddActivity::class.java)
            startActivity(intent)
        }
    }
}
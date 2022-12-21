package com.charging.notesapp.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.charging.notesapp.NoteApplication
import com.charging.notesapp.R
import com.charging.notesapp.adapter.NoteAdapter
import com.charging.notesapp.model.Note
import com.charging.notesapp.viewmodel.NoteViewModel
import com.charging.notesapp.viewmodel.NoteViewModelFactory
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var addActivityResultLauncher : ActivityResultLauncher<Intent>

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
            if (isChecked) {
                chipView.animate().rotation(360f).setDuration(1000).start()
                Handler(Looper.getMainLooper()).postDelayed({
                    chipView.text = "grid"
                    chipView.setChipIconResource(R.drawable.ic_round_grid_on_24)
                    recyclerView.layoutManager =
                        StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                }, 500)
            } else {
                chipView.animate().rotation(360f).setDuration(1000).start()
                Handler(Looper.getMainLooper()).postDelayed({
                    chipView.text = "list"
                    chipView.setChipIconResource(R.drawable.ic_round_grid_on_24)
                    recyclerView.layoutManager =
                        StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                }, 500)
            }
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

        // register activity for result
        registerActivityResultLauncher()

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
        noteViewModel.myAllNotes.observe(this) { notes ->
            // update UI
            noteAdapter.setNote(notes)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                viewHolder.adapterPosition
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note Deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(recyclerView)

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity,NoteAddActivity::class.java)
            addActivityResultLauncher.launch(intent)
        }
    }

     private fun registerActivityResultLauncher(){
        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultAddNode ->
                val resultCode = resultAddNode.resultCode
                val data = resultAddNode.data

                if (resultCode == RESULT_OK && data != null){
                    val noteTitle:String = data.getStringExtra("title").toString()
                    val noteDesc:String = data.getStringExtra("desc").toString()

                    val note = Note(noteTitle,noteDesc)
                    noteViewModel.insert(note)
                }

            })
     }
}
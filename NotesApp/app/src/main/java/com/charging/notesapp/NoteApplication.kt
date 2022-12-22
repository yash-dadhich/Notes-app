package com.charging.notesapp

import android.app.Application
import com.charging.notesapp.model.NoteDatabase
import com.charging.notesapp.repository.NoteRepository
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication:Application() {

    companion object{
        var isFirstTime= false
        var theme = "light"
    }


    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NoteDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { NoteRepository(database.getNoteDao()) }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        Hawk.put("viewType", "list")
    }

}
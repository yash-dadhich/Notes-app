package com.charging.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(val title:String,val desc:String) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}
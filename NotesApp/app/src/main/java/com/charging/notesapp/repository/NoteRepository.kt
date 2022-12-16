package com.charging.notesapp.repository

import androidx.annotation.WorkerThread
import com.charging.notesapp.room.NoteDAO
import com.charging.notesapp.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDAO) {

    val myAllNotes:Flow<List<Note>> = noteDAO.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note){
        noteDAO.insert(note)
    }
    @WorkerThread
    suspend fun update(note: Note){
        noteDAO.update(note)
    }
    @WorkerThread
    suspend fun delete(note: Note){
        noteDAO.delete(note)
    }
    @WorkerThread
    suspend fun deleteAllNotes(){
        noteDAO.deleteAllNotes()
    }
}
package com.charging.notesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.charging.notesapp.R
import com.charging.notesapp.model.Note

class NoteAdapter(): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var notes:List<Note> = ArrayList()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewTitle:TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDesc:TextView = itemView.findViewById(R.id.textViewDescription)
        val cardView:CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentNote:Note = notes[position]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDesc.text = currentNote.desc
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNote(myNotes:List<Note>){
        this.notes = myNotes
        notifyDataSetChanged()
    }

    fun getNote(position:Int):Note{
        return notes[position]
    }
}
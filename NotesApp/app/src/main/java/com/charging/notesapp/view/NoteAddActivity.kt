package com.charging.notesapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.charging.notesapp.R


class NoteAddActivity : AppCompatActivity() {

    lateinit var backImage:ImageView
    lateinit var textOk:TextView
    lateinit var textTitle:EditText
    lateinit var textDesc:EditText
    lateinit var buttonBold: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)

        backImage = findViewById(R.id.imgBack)
        textOk = findViewById(R.id.textOk)
        textTitle = findViewById(R.id.editTextTitle)
        textDesc = findViewById(R.id.editTextDesc)
        buttonBold = findViewById(R.id.buttonBold)

        backImage.setOnClickListener {
            finish()
        }

        textOk.setOnClickListener {
            saveNote()
        }

        buttonBold.setOnClickListener {
            var start = textDesc.selectionStart
            var end = textDesc.selectionEnd

            val selectedText = textDesc.text.substring(start, end)
            Log.d("text123", selectedText)
        }
    }

    private fun saveNote() {
        val noteTitle = textTitle.text.toString()
        val noteDesc = textDesc.text.toString()

        val intent = Intent()
        intent.putExtra("title", noteTitle)
        intent.putExtra("desc", noteDesc)
        setResult(RESULT_OK, intent)
        finish()

    }
}
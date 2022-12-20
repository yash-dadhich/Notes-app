package com.charging.notesapp.view

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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


        var start = textDesc.selectionStart
        var end = textDesc.selectionEnd

        val selectedText = textDesc.text.substring(start,end)
        Log.d("text123", selectedText)

        textOk.setOnClickListener {
            var start = textDesc.selectionStart
            var end = textDesc.selectionEnd

            val selectedText = textDesc.text.substring(start,end)
            Log.d("text123", selectedText)
        }

        textDesc.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener{
            override fun onViewAttachedToWindow(v: View?) {
                Log.d("text123", selectedText)
            }

            override fun onViewDetachedFromWindow(v: View?) {
                Log.d("text123", selectedText)
            }

        })

        buttonBold.setOnClickListener {
            var start = textDesc.selectionStart
            var end = textDesc.selectionEnd

            val selectedText = textDesc.text.substring(start,end)
            Log.d("text123", selectedText)

            
        }

        textDesc.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->

            Log.d("text123", selectedText)

        }



    }

    override fun onResume() {
        super.onResume()

        var start = textDesc.selectionStart
        var end = textDesc.selectionEnd

        val selectedText = textDesc.text.substring(start,end)
        Log.d("text123", selectedText)
    }

}
package com.example.notessqlite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityAddNotesBinding
import com.example.notessqlite.model.Note
import com.example.notessqlite.model.NoteDatabaseHelper

class AddNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding //declare the binding variable for the layout
    private lateinit var db: NoteDatabaseHelper //declare db helper variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater) //init binding var
        setContentView(binding.root) //set content view to the root of binding

        db = NoteDatabaseHelper(this) //init db helper

        //set the click listener  for the save button to insert a note and show a message
        binding.savebutton.setOnClickListener {
            val title = binding.editTextTitle.text.toString() //get title form the edittext
            val content = binding.editTextDescription.text.toString() //get content form edittext

            val note = Note(0, title, content) //create a new note obj
            db.insertNote(note) //insert note in db
            finish() //finish the activity
            Toast.makeText(applicationContext, "Note Saved Successfully", Toast.LENGTH_SHORT).show() //show a toast msg
        }
    }
}
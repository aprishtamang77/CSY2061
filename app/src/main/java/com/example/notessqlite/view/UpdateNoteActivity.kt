package com.example.notessqlite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding
import com.example.notessqlite.model.Note
import com.example.notessqlite.model.NoteDatabaseHelper

//define UpdateNoteActivity class that extends AppCompatActivity
class UpdateNoteActivity : AppCompatActivity() {

    //declare lateinit properties for view binding and database helper
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper
    // Initialize noteId with a default value of -1
    private var noteId: Int = -1

    //override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate the layout using view binding
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        //set content view to the root view of the binding
        setContentView(binding.root)

        //init the database helper
        db = NoteDatabaseHelper(this)

        //get noteId from the intent extras, defaulting to -1 if not found
        noteId = intent.getIntExtra("note_id", -1)
        //if noteId is -1, finish the activity and return
        if (noteId == -1) {
            finish()
            return
        }

        //retrieve note from the database using the noteId
        val note = db.getNoteById(noteId)
        //set retrieved note's title and content to the corresponding EditText fields
        binding.editTextUpdateTitle.setText(note.title)
        binding.editTextUpdateContent.setText(note.content)

        //set OnClickListener for the update button
        binding.updatebutton.setOnClickListener {
            //get new title and content from the EditText fields
            val newTitle = binding.editTextUpdateTitle.text.toString()
            val newContent = binding.editTextUpdateContent.text.toString()

            //create new Note object with the updated information
            val updateNote = Note(noteId, newTitle, newContent)
            // Update the note in the database
            db.updateNote(updateNote)
            //finish the activity
            finish()
            //show toast message indicating the note was updated successfully
            Toast.makeText(applicationContext, "Note Updated Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}

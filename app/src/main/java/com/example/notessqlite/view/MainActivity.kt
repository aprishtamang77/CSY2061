package com.example.notessqlite.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notessqlite.R
import com.example.notessqlite.databinding.ActivityMainBinding
import com.example.notessqlite.model.NoteDatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //a lateinit var for view bind
    private lateinit var recyclerView: RecyclerView //a lateinit var for the RecyclerView
    private lateinit var adapter: NoteAdapter //a lateinit var for the RecyclerView adapter
    private lateinit var db: NoteDatabaseHelper //a lateinit var for the database helper
    private lateinit var userNameTextView: TextView //a lateinit var for the TextView to display the username

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //call super class onCreate method
        binding = ActivityMainBinding.inflate(layoutInflater) //init view binding
        setContentView(binding.root) //st content view to root of the bind

        userNameTextView = findViewById(R.id.userNameTextView) //find username TextView by id

        //get username from intent
        val username = intent.getStringExtra("USERNAME") //retrieve username from the intent extras
        if (username != null) { //check if the username is not null
            userNameTextView.text = username //set username to the TextView
        }
        //retrieve data from the database and display it
        retrieveData() //call method to retrieve data from the db
        //set up  button click listener to create a new note
        buttonClick() //call method to set up the button click listener
        //set up swipe to delete functionality for the RecyclerView
        swipeToDelete() //call method to set up swipe to delete func
        //setup the search func
        setupSearch() //call method to set up the search func
        //setup quiz button function
        setupQuizButton() //call method to set up the quiz button func
    }

    private fun setupQuizButton() {
        val buttonQuiz: Button = findViewById(R.id.buttonQuiz) //find quiz button by id
        buttonQuiz.setOnClickListener { //set OnClickListener on the quiz button
            val intent = Intent(this, QuizActivity::class.java) //create Intent to start the QuizActivity
            startActivity(intent) //start QuizActivity
        }
    }

    private fun setupSearch() {
        val searchView = binding.searchView //get SearchView from the binding
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener { //set OnQueryTextListener on the SearchView
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false //return false to indicate that we don't handle query submission
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //perform search when the user enters text
                newText?.let { //check if the newText is not null
                    val searchResults = db.searchNotes(it) //search db for notes matching the query
                    adapter.refreshData(searchResults) //refresh adapter with the search results
                }
                return true //return true to indicate that we handle query text changes
            }
        })
    }

    private fun swipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT //define swipe directions
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false //return false because we don't support moving items
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition //get position of the swiped item
                val note = adapter.getNoteAtPosition(position) //get note at the swiped position
                db.deleteNoteById(note.id) //deletethe note from the database
                adapter.refreshData(db.getAllNotes()) //refresh adapter with the updated data
                Toast.makeText(
                    this@MainActivity,
                    "Note Deleted Successfully",
                    Toast.LENGTH_SHORT
                ).show() //show a toast message indicating successful deletion
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView) //attach ItemTouchHelper to the RecyclerView
    }

    private fun retrieveData() {
        recyclerView = binding.recyclerView //get RecyclerView from the binding
        val layoutManager = LinearLayoutManager(this) //create LinearLayoutManager
        recyclerView.layoutManager = layoutManager //set layout manager for the RecyclerView
        db = NoteDatabaseHelper(this) //init the database helper

        //fetch data from the database and set it to the RecyclerView
        val notes = db.getAllNotes() //get all notes from the database
        adapter = NoteAdapter(notes, this) //create new adapter with the notes data
        recyclerView.adapter = adapter //set adapter for the RecyclerView
    }

    private fun buttonClick() {
        binding.addButton.setOnClickListener { //set OnClickListener on the add button
            val intent = Intent(this, AddNotesActivity::class.java) //create Intent to start the AddNotesActivity
            startActivity(intent) //start ddNotesActivity
        }
    }

    override fun onResume() {
        super.onResume() //call super class onResume method

        //fetch updated data from the database and refresh the RecyclerView
        val updatedNotes = db.getAllNotes() //getall notes from the database
        adapter.refreshData(updatedNotes) //refreshadapter with the updated data
    }
}

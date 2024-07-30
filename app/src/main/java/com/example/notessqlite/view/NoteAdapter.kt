package com.example.notessqlite.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notessqlite.databinding.ListItemNoteBinding
import com.example.notessqlite.model.Note
import com.example.notessqlite.model.NoteDatabaseHelper


class NoteAdapter(private var notes: List<Note>, context: Context) : RecyclerView.Adapter<NoteViewHolder>() {

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context) //init database helper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        //inflate layout for each item in the RecyclerView
        val binding = ListItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding) //return new instance of NoteViewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position] //get note at the specified position

        holder.title.text = note.title //set title of the note to the TextView
        holder.content.text = note.content //set content of the note to the TextView

        holder.edit.setOnClickListener {
            //create Intent to start the UpdateNoteActivity
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id) //pass note ID to the UpdateNoteActivity
            }
            holder.itemView.context.startActivity(intent) //start UpdateNoteActivity
        }

        holder.del.setOnClickListener {
            //del note from the database
            db.deleteNoteById(note.id)
            refreshData(db.getAllNotes()) // Refresh the adapter with the updated data
            Toast.makeText(holder.itemView.context, "Note Deleted Successfully", Toast.LENGTH_SHORT)
                .show() //display toast message indicating successful deletion
        }

        holder.shareNote.setOnClickListener {
            //share note
            shareNote(holder.itemView.context, note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size //return total number of notes
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newNotes: List<Note>){
        notes = newNotes //update notes list with new data
        notifyDataSetChanged() //notify adapter that the data has changed
    }

    //for swipe to delete
    fun getNoteAtPosition(position: Int): Note {
        return notes[position] //return note at the specified position
    }

    //share note
    private fun shareNote(context: Context, note: Note){
        val shareIntent = Intent(Intent.ACTION_SEND) //create Intent for sharing data
        shareIntent.type = "text/plain" //set type of data to be shared
        val shareMessage = "Note Title: ${note.title}\nNote Content: ${note.content}" //create message to be shared
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage) //add message to the Intent
        context.startActivity(Intent.createChooser(shareIntent, "Share Note")) //start chooser for sharing the note
    }
}

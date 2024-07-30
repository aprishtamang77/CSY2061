package com.example.notessqlite.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
// a class for managing a SQLite database for notes
class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // here creating the companion object to hold static constants
    companion object{
        private const val DATABASE_NAME = "notesapp.db" // db name
        private const val DATABASE_VERSION = 1 // db version
        private const val TABLE_NAME = "allnotes" // Table name
        private const val COLUMN_ID = "id" // col for ID
        private const val COLUMN_TITLE = "title" // Col for title
        private const val COLUMN_CONTENT = "content" // Col  for content
    }


    //calls when the db is created
    override fun onCreate(db: SQLiteDatabase?) {
        // to create a new table
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery) // this is to execute the query
    }

    // calls when the db is upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // to drop table if it exists
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery) // exe the query
        onCreate(db) // create the new table
    }

    // this is a function to insert a new note into the db

    fun insertNote(note: Note) {
        val db = writableDatabase // get db in write mode
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title) // put title in content value
            put(COLUMN_CONTENT, note.content) // put content in the content value
        }
        db.insert(TABLE_NAME, null, values) // insert in the row
        db.close() // close the db connection
    }

    // fun to retrieve all notes form db
    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>() //  a mutable list to hold note
        val db = readableDatabase // get db in read mode
        val columns = arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT) // col to retrieve
        val cursor: Cursor = db.query(
            TABLE_NAME, columns, null, null, null, null, "$COLUMN_ID DESC" // perform the query
        )

        // check if the cursor is valid then move to the first row
        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val titleIndex = cursor.getColumnIndex(COLUMN_TITLE)
                val contentIndex = cursor.getColumnIndex(COLUMN_CONTENT)

                // check if the col index is valid or not
                if (idIndex != -1 && titleIndex != -1 && contentIndex != -1) {
                    val id = cursor.getInt(idIndex) // get id
                    val title = cursor.getString(titleIndex) //get title
                    val content = cursor.getString(contentIndex) // get content
                    notes.add(Note(id, title, content)) //add note to list
                } else {
                    //handle case where a col index is not found
                }
            } while (cursor.moveToNext()) //move to the next row
        }

        cursor.close() //close the cursor
        db.close() //close db connection
        return notes //return the list of the notes
    }

    // fun to update the existing note
    fun updateNote(note: Note) {
        val db = writableDatabase //get db in the write mode
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title) //put the updated title
            put(COLUMN_CONTENT, note.content) //put the updated content
        }
        db.update(TABLE_NAME, values,
            "$COLUMN_ID = ?", arrayOf(note.id.toString()) //update the row with the specified ID
        )
        db.close() //close the db connection
    }

    //fun to get the note by id
    fun getNoteById(noteId: Int): Note {
        val db = readableDatabase //get db in reade mode
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId" //select note by id
        val cursor = db.rawQuery(query, null) //execute
        cursor.moveToFirst() //move to the first row

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)) //get the id
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)) //get the title
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)) //get the content

        cursor.close() //close the cursor
        db.close() //close the db connection
        return Note(id, title, content) //return the note
    }

    //fun to del note by id
    fun deleteNoteById(noteId: Int) {
        val db = writableDatabase //get db in write mode
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(noteId.toString())) //del row with spec id
        db.close() //close the db connection
    }

    //fun to search note by title
    fun searchNotes(query: String): List<Note> {
        val notes = mutableListOf<Note>() //create mutable list to hold note
        val db = readableDatabase //get the db in the read mode
        val columns = arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT) //define the col to retrieve
        val cursor: Cursor = db.query(
            TABLE_NAME, columns, "$COLUMN_TITLE LIKE ? OR $COLUMN_CONTENT LIKE ?",
            arrayOf("%$query%", "%$query%"), null, null, "$COLUMN_ID DESC" //perform query with search condition
        )

        //check the cursor is valid or not then move to the first row
        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val titleIndex = cursor.getColumnIndex(COLUMN_TITLE)
                val contentIndex = cursor.getColumnIndex(COLUMN_CONTENT)

                //check if the col index is valid or not
                if (idIndex != -1 && titleIndex != -1 && contentIndex != -1) {
                    val id = cursor.getInt(idIndex) //get id
                    val title = cursor.getString(titleIndex) //get title
                    val content = cursor.getString(contentIndex) //get content
                    notes.add(Note(id, title, content)) //add note to list
                } else {
                    //handle the case where the col index is not found
                }
            } while (cursor.moveToNext()) //move to the next row
        }

        cursor.close() //close the cursor
        db.close() //close the db connection
        return notes //return the list of note
    }
}
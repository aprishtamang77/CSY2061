package com.example.notessqlite.model

// defining  a data class Note, which is a  way to create classes to hold the data
data class Note(
    // to represent the unique ID for each note
    val id: Int,

    // represents title of note
    val title: String,

    // represents content of note
    val content: String
)


////folder.kt
//data class Folder(
//    val id: Int,
//    val name: String
//)

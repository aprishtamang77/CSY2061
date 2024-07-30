package com.example.notessqlite.view

import androidx.recyclerview.widget.RecyclerView
import com.example.notessqlite.databinding.ListItemNoteBinding

//class NoteViewHolder that extends RecyclerView.ViewHolder and takes a ListItemNoteBinding as a parameter
class NoteViewHolder(binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    //init title property by binding it to the textViewTitle in the layout
    val title = binding.textViewTitle

    //init content property by binding it to the textViewContent in the layout
    val content = binding.textViewContent

    //init edit property by binding it to the imgEdit in the layout
    val edit = binding.imgEdit

    //init del property by binding it to the imgDel in the layout
    val del = binding.imgDel

    //init shareNote property by binding it to the imgShare in the layout
    val shareNote = binding.imgShare
}

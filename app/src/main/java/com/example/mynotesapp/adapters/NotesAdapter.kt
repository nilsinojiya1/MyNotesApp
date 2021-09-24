package com.example.mynotesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.MainActivity
import com.example.mynotesapp.R
import com.example.mynotesapp.databinding.ItemNoteBinding
import com.example.mynotesapp.models.Note

class NotesAdapter(onNoteListener: OnNoteListener) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var notes = mutableListOf<Note>()
    var onNoteListener: OnNoteListener = onNoteListener
    fun setNoteList(notes: List<Note>){
        this.notes = notes.toMutableList()
        notifyDataSetChanged()
    }

    class NotesViewHolder(itemView: View, onNoteListener: OnNoteListener): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        private val binding = ItemNoteBinding.bind(itemView)
        var onNoteListener: OnNoteListener = onNoteListener
        var isCheck = false
        fun bind(note: Note){
            with(binding){
                tvTitle.text = note.title
                tvDescription.text = note.description
                tvDescription.setOnClickListener {
                    if(!isCheck){
                        tvDescription.maxLines = Integer.MAX_VALUE
                        isCheck = true
                    } else {
                        tvDescription.maxLines = 3
                        isCheck = false
                    }
                }
                ivEdit.setOnClickListener(this@NotesViewHolder)
            }

            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            onNoteListener.onNoteClick(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            onNoteListener.onNoteLongClick(adapterPosition)
            return true
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        return NotesViewHolder(view, onNoteListener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    public interface OnNoteListener{
        fun onNoteClick(position: Int)
        fun onNoteLongClick(position: Int)
    }

}
package com.example.mynotesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mynotesapp.MainActivity
import com.example.mynotesapp.databinding.ActivityAddNotesBinding
import com.example.mynotesapp.models.Note
import com.example.mynotesapp.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNotesActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    private var _binding: ActivityAddNotesBinding? = null
    private val binding get() = _binding!!
    val notesViewModel: NotesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: activitystart")

        binding.viewModel = notesViewModel
        binding.lifecycleOwner = this

        var note: Note
        var intent = intent
        var id:Int = 0
        var isSync = false
        if(intent.getStringExtra("FROM").equals("UPDATE")){
            note = intent.getParcelableExtra<Note>("NOTE")!!
            id = note.id
            binding.tvTitle.text = "Update Note"
            notesViewModel.noteTitle.value = note.title.toString()
            notesViewModel.description.value = note.description.toString()
        }

        binding.fabSave.setOnClickListener {
           if(!TextUtils.isEmpty(binding.etTitle.text.toString())){
               if(intent.getStringExtra("FROM").equals("UPDATE")){
                   notesViewModel.update(Note(id, binding.etTitle.text.toString(), binding.etDescription.text.toString(), isSync))
               } else {
                   notesViewModel.saveOrUpdate()
               }
               //startActivity(Intent(this, MainActivity::class.java))
               finish()
           } else{
               Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show()
           }
        }

    }
}
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var nameViewModel: NameViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameViewModel = ViewModelProvider(this)[NameViewModel::class.java]

        val inputName = findViewById<EditText>(R.id.inputName)
        val addButton = findViewById<Button>(R.id.addButton)
        val searchBox = findViewById<EditText>(R.id.searchBox)
        recyclerView = findViewById(R.id.recyclerView)

        adapter = NameAdapter(mutableListOf()) { position ->
            nameViewModel.deleteName(position)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        nameViewModel.nameList.observe(this) { updatedList ->
            adapter = NameAdapter(updatedList) { position ->
                nameViewModel.deleteName(position)
            }
            recyclerView.adapter = adapter
        }

        searchBox.addTextChangedListener {
            nameViewModel.filter(it.toString())
        }

        nameViewModel.filteredList.observe(this) { updatedList ->
            adapter = NameAdapter(updatedList.toMutableList()) { position ->
                nameViewModel.deleteName(position)
            }
            recyclerView.adapter = adapter
        }

        addButton.setOnClickListener {
            val name = inputName.text.toString().trim()
            if (name.isNotEmpty()) {
                nameViewModel.addName(Name(name))
                inputName.text.clear()
            }
        }
    }
}

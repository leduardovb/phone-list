package com.example.phonelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addContactFloatingButton: FloatingActionButton = findViewById(R.id.add)
        addContactFloatingButton.setOnClickListener {
            view -> when(view.id) {
                R.id.add -> {
                    Toast.makeText(this, "Eita funfou", Toast.LENGTH_LONG).show()
                }
            }
        }
        val contacts: ArrayList<Contact> = ArrayList()
        contacts.add(Contact("Eduardo", "(44) 9 9928-5054", "Rua teste", "Dev"))
        contacts.add(Contact("Maria", "(44) 9 9928-5054", "Rua teste", "Dev"))
        contacts.add(Contact("Guilherme", "(44) 9 9928-5054", "Rua teste", "Dev"))
        val listView: ListView = findViewById(R.id.contacts)
        val adapter = PhoneListAdapter(this, R.layout.list_item, contacts)
        listView.adapter = adapter
    }
}
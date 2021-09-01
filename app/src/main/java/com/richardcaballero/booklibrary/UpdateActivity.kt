package com.richardcaballero.booklibrary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {

    lateinit var titleInput: EditText
    lateinit var authorInput: EditText
    lateinit var pagesInput: EditText
    lateinit var updateButton: Button
    lateinit var deleteButton: Button

    lateinit var id: String
    lateinit var title: String
    lateinit var author: String
    lateinit var pages: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        titleInput = findViewById(R.id.titleInput1)
        authorInput = findViewById(R.id.authorInput1)
        pagesInput = findViewById(R.id.pagesInput1)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)

        getAndSetIntentData()

        supportActionBar?.title = title

        updateButton.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            title = titleInput.text.toString().trim()
            author = authorInput.text.toString().trim()
            pages = pagesInput.text.toString().trim()
            myDB.updateData(id, title, author, pages)
        }

        deleteButton.setOnClickListener { confirmDialog() }
    }

    fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
                intent.hasExtra("author") && intent.hasExtra("pages")) {
            // Getting data from intent
            id = intent.getStringExtra("id")!!
            title = intent.getStringExtra("title")!!
            author = intent.getStringExtra("author")!!
            pages = intent.getStringExtra("pages")!!
            // Setting intent data
            titleInput.setText(title)
            authorInput.setText(author)
            pagesInput.setText(pages)
        }
        else Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
    }

    fun confirmDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Delete $title ?")
            setMessage("Are you sure you want to delete $title ?")
            setPositiveButton("Yes") { _, _ ->
                val myDB = MyDatabaseHelper(this@UpdateActivity)
                myDB.deleteOneRow(id)
                finish()
            }
            setNegativeButton("No") { _, _ -> }
            create()
            show()
        }
    }
}
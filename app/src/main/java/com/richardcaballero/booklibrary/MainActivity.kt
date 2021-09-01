package com.richardcaballero.booklibrary

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var emptyImageView: ImageView
    lateinit var noData: TextView

    lateinit var myDB: MyDatabaseHelper
    lateinit var bookId: ArrayList<String>
    lateinit var bookTitle: ArrayList<String>
    lateinit var bookAuthor: ArrayList<String>
    lateinit var bookPages: ArrayList<String>
    lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.add_button)
        emptyImageView = findViewById(R.id.emptyImageView)
        noData = findViewById(R.id.noData)

        addButton.setOnClickListener { startActivity(Intent(this, AddActivity::class.java)) }

        myDB = MyDatabaseHelper(this)
        bookId = ArrayList()
        bookTitle = ArrayList()
        bookAuthor = ArrayList()
        bookPages = ArrayList()

        storeDataInArrays()

        customAdapter = CustomAdapter(this, this, bookId, bookTitle, bookAuthor, bookPages)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) recreate()
    }

    fun storeDataInArrays() {
        val cursor: Cursor? = myDB.readAllData()
        if (cursor?.count == 0) {
            emptyImageView.visibility = View.VISIBLE
            noData.visibility = View.VISIBLE
        }
        else {
            while (cursor?.moveToNext() == true) {
                bookId.add(cursor.getString(0))
                bookTitle.add(cursor.getString(1))
                bookAuthor.add(cursor.getString(2))
                bookPages.add(cursor.getString(3))
            }
            emptyImageView.visibility = View.GONE
            noData.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) confirmDialog()
        return super.onOptionsItemSelected(item)
    }

    fun confirmDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Delete all?")
            setMessage("Are you sure you want to delete all data?")
            setPositiveButton("Yes") { _, _ ->
                val myDB = MyDatabaseHelper(this@MainActivity)
                myDB.deleteAllData()
                // Refresh activity
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            setNegativeButton("No") { _, _ -> }
            create()
            show()
        }
    }
}
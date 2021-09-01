package com.richardcaballero.booklibrary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_AUTHOR TEXT, " +
                "$COLUMN_PAGES INTEGER);"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    companion object {
        private const val DATABASE_NAME = "BookLibrary.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "my_library"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "book_title"
        private const val COLUMN_AUTHOR = "book_author"
        private const val COLUMN_PAGES = "book_pages"
    }

    fun addBook(title: String, author: String, pages: Int) {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_AUTHOR, author)
            put(COLUMN_PAGES, pages)
        }
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show()
    }

    fun readAllData(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) cursor = db.rawQuery(query, null)
        return cursor
    }

    fun updateData(rowId: String, title: String, author: String, pages: String) {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_AUTHOR, author)
            put(COLUMN_PAGES, pages)
        }
        val result = db.update(TABLE_NAME, cv, "_id = ?", arrayOf(rowId))
        if (result == 0) Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show()
    }

    fun deleteOneRow(row_id: String) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "_id = ?", arrayOf(row_id))
        if (result == 0) Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}
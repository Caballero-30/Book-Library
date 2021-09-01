package com.richardcaballero.booklibrary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter internal constructor(
        private val activity: Activity, private val context: Context,
        private val bookId: ArrayList<String>, private val bookTitle: ArrayList<String>,
        private val bookAuthor: ArrayList<String>, private val bookPages: ArrayList<String>,
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bookIdTxt.text = bookId[position]
        holder.bookTitleTxt.text = bookTitle[position]
        holder.bookAuthorTxt.text = bookAuthor[position]
        holder.bookPagesTxt.text = bookPages[position]
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java).apply {
                putExtra("id", bookId[position])
                putExtra("title", bookTitle[position])
                putExtra("author", bookAuthor[position])
                putExtra("pages", bookPages[position])
            }
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int { return bookId.size }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bookIdTxt: TextView
        var bookTitleTxt: TextView
        var bookAuthorTxt: TextView
        var bookPagesTxt: TextView
        var mainLayout: LinearLayout
        var translateAnim: Animation

        init {
            bookIdTxt = itemView.findViewById(R.id.bookIdTxt)
            bookTitleTxt = itemView.findViewById(R.id.bookTitleTxt)
            bookAuthorTxt = itemView.findViewById(R.id.bookAuthorTxt)
            bookPagesTxt = itemView.findViewById(R.id.bookPagesTxt)
            mainLayout = itemView.findViewById(R.id.mainLayout)
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim)
            mainLayout.animation = translateAnim
        }
    }
}
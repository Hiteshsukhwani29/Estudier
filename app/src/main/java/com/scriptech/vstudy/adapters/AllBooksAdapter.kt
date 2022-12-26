package com.scriptech.vstudy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scriptech.vstudy.R
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.ui.fragments.allBooks.AllBooksDirections
import com.squareup.picasso.Picasso

class AllBooksAdapter() :
    RecyclerView.Adapter<AllBooksAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Books>() {
        override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_new_books, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = differ.currentList[position]
        Picasso.get().load(book.book_img).into(holder.BookImage)
        holder.BookauthName.text = book.book_author
        holder.BookName.text = book.book_author
        holder.Book.setOnClickListener {
            it.findNavController().navigate(
                AllBooksDirections.actionAllBooksToPdf(book.book_link!!)
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val Book = itemView.findViewById<CardView>(R.id.card_new_book)
        val BookImage = itemView.findViewById<ImageView>(R.id.img_new_book)
        val BookauthName = itemView.findViewById<TextView>(R.id.txt_new_bookauthorname)
        val BookName = itemView.findViewById<TextView>(R.id.txt_new_bookname)

    }
}
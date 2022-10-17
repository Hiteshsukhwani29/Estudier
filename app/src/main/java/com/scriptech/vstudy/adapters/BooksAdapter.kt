package com.scriptech.vstudy.adapters

import android.os.Bundle
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
import com.scriptech.vstudy.ui.fragments.home.HomeDirections
import com.scriptech.vstudy.ui.fragments.home.HomeViewModel
import com.squareup.picasso.Picasso

class BooksAdapter(val type: Int = 1) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Books>() {
        override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem.book_link == newItem.book_link
        }

        override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_trending_book, parent, false)
        if (type == 2) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_new_books, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = differ.currentList[position]
        if (type == 2) {
            Picasso.get().load(book.book_img).into(holder.BookImage1)
            holder.BookauthName1.text = book.book_author
            holder.BookName1.text = book.book_author
            holder.Book1.setOnClickListener {
                it.findNavController().navigate(
                    AllBooksDirections.actionAllBooksToPdf(book.book_link!!)
                )
            }
        } else {
            Picasso.get().load(book.book_img).into(holder.BookImage)
            holder.BookauthName.text = book.book_author
            holder.Book.setOnClickListener {
                it.findNavController().navigate(
                    AllBooksDirections.actionAllBooksToPdf(book.book_link!!)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val Book = itemView.findViewById<CardView>(R.id.card_trending_book)
        val BookImage = itemView.findViewById<ImageView>(R.id.book_image)
        val BookauthName = itemView.findViewById<TextView>(R.id.book_name)
        val Book1 = itemView.findViewById<CardView>(R.id.card_new_book)
        val BookImage1 = itemView.findViewById<ImageView>(R.id.img_new_book)
        val BookauthName1 = itemView.findViewById<TextView>(R.id.txt_new_bookauthorname)
        val BookName1 = itemView.findViewById<TextView>(R.id.txt_new_bookname)

    }
}
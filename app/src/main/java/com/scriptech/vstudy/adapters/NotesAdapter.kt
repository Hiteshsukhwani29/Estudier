package com.scriptech.vstudy.adapters

import android.os.Bundle
import android.util.Log
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
import com.scriptech.vstudy.model.DepartmentModel
import com.scriptech.vstudy.model.Notes
import com.scriptech.vstudy.ui.fragments.departments.DepartmentViewModel
import com.scriptech.vstudy.ui.fragments.departments.DepartmentsDirections
import com.scriptech.vstudy.ui.fragments.home.HomeDirections
import com.scriptech.vstudy.ui.fragments.home.HomeViewModel
import com.scriptech.vstudy.ui.fragments.subject.SubjectDirections
import com.squareup.picasso.Picasso

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.notes_link == newItem.notes_link
        }

        override fun areContentsTheSame(
            oldItem: Notes,
            newItem: Notes
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_subject, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.NoteName.text = note.notes_name
        holder.NoteName.setOnClickListener {
            it.findNavController().navigate(
                SubjectDirections.actionSubjectToPdf(note.notes_link!!)
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val NoteName = itemView.findViewById<TextView>(R.id.txt_sub_notes)
    }
}
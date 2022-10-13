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
import com.scriptech.vstudy.model.DepartmentModel
import com.scriptech.vstudy.ui.fragments.departments.DepartmentViewModel
import com.scriptech.vstudy.ui.fragments.departments.DepartmentsDirections
import com.scriptech.vstudy.ui.fragments.home.HomeDirections
import com.scriptech.vstudy.ui.fragments.home.HomeViewModel
import com.squareup.picasso.Picasso

class DepartmentAdapter(var viewModel: DepartmentViewModel? = null) :
    RecyclerView.Adapter<DepartmentAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DepartmentModel>() {
        override fun areItemsTheSame(oldItem: DepartmentModel, newItem: DepartmentModel): Boolean {
            return oldItem.sub_link == newItem.sub_link
        }

        override fun areContentsTheSame(
            oldItem: DepartmentModel,
            newItem: DepartmentModel
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
        val dept = differ.currentList[position]
        holder.SubName.text = dept.sub_name
        holder.SubName.setOnClickListener {
            it.findNavController().navigate(
                DepartmentsDirections.actionDepartmentsToSubject(dept.sub_name!!, dept.sub_link!!)
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val SubName = itemView.findViewById<TextView>(R.id.txt_sub_notes)
    }
}
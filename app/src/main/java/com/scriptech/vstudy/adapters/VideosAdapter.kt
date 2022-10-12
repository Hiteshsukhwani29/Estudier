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
import com.scriptech.vstudy.model.Videos
import com.scriptech.vstudy.ui.fragments.home.HomeDirections
import com.scriptech.vstudy.ui.fragments.home.HomeViewModel
import com.squareup.picasso.Picasso

class VideosAdapter(var viewModel: HomeViewModel? = null) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Videos>() {
        override fun areItemsTheSame(oldItem: Videos, newItem: Videos): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: Videos, newItem: Videos): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_new_books, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = differ.currentList[position]
        Picasso.get().load(video.img).into(holder.VideoImage)
        holder.VideoImage.setOnClickListener {
            it.findNavController().navigate(
                HomeDirections.actionHome2ToVideoPlayer(video.link)
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val VideoImage = itemView.findViewById<ImageView?>(R.id.video_image)
    }
}
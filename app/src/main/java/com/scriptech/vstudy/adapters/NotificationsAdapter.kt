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
import com.scriptech.vstudy.model.Notes
import com.scriptech.vstudy.model.Notification
import com.scriptech.vstudy.ui.fragments.departments.DepartmentViewModel
import com.scriptech.vstudy.ui.fragments.departments.DepartmentsDirections
import com.scriptech.vstudy.ui.fragments.home.HomeDirections
import com.scriptech.vstudy.ui.fragments.home.HomeViewModel
import com.scriptech.vstudy.ui.fragments.notifications.NotificationsDirections
import com.scriptech.vstudy.ui.fragments.subject.SubjectDirections
import com.squareup.picasso.Picasso

class NotificationsAdapter() :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.notification_link == newItem.notification_link
        }

        override fun areContentsTheSame(
            oldItem: Notification,
            newItem: Notification
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = differ.currentList[position]
        holder.NotificationName.text = notification.notification_name
        holder.NotificationDate.text = notification.notification_date
        Picasso.get().load(notification.notification_img).into(holder.NotificationImage)
        holder.NotificationCard.setOnClickListener {
            it.findNavController().navigate(
                NotificationsDirections.actionNavigationNotificationsToWebview(notification.notification_link)
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val NotificationCard = itemView.findViewById<CardView>(R.id.notification)
        val NotificationImage = itemView.findViewById<ImageView>(R.id.notification_img)
        val NotificationName = itemView.findViewById<TextView>(R.id.notification_name)
        val NotificationDate = itemView.findViewById<TextView>(R.id.notification_date)
    }
}
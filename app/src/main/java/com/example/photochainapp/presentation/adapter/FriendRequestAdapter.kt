package com.example.photochainapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photochainapp.databinding.RequestItemBinding

class FriendRequestAdapter(
    private val requests: List<Pair<String, String>>,
    private val onAccept: (String) -> Unit,
    private val onDecline: (String) -> Unit
) : ListAdapter<Pair<String, String>, FriendRequestAdapter.RequestViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = RequestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val (userId, email) = requests[position]
        holder.bind(email)
        holder.binding.acceptBtn.setOnClickListener { onAccept(userId) }
        holder.binding.declineBtn.setOnClickListener { onDecline(userId) }
    }

    class RequestViewHolder(val binding: RequestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(email: String) {
            binding.mailOnRequest.text = email
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<String, String>>() {
            override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>) = oldItem.first == newItem.first
            override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>) = oldItem == newItem
        }
    }
}
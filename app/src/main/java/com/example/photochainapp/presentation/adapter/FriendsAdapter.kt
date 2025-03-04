package com.example.photochainapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photochainapp.databinding.FriendsItemBinding

class FriendsAdapter(
    private val friends: List<Pair<String, String>>, // (userId, email)
    private val onRemoveFriend: (String) -> Unit
) : ListAdapter<Pair<String, String>, FriendsAdapter.FriendViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = FriendsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val (userId, email) = friends[position]
        holder.bind(email)
        holder.binding.emailFriends.setOnLongClickListener {
            onRemoveFriend(userId)
            true
        }
    }

    class FriendViewHolder(val binding: FriendsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(email: String) {
            binding.emailFriends.text = email
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<String, String>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ) = oldItem.first == newItem.first

            override fun areContentsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ) = oldItem == newItem
        }
    }
}
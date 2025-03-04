package com.example.photochainapp.domain.repository

import com.example.photochainapp.domain.model.User

interface FriendsRepository {
    fun initializeUserFirestore(userId: String, name: String, email: String)
    fun sendFriendRequest(currentUserId: String, targetUserId: String)
    fun acceptFriendRequest(currentUserId: String, senderId: String)
    fun getFriends(userId: String, callback: (List<String>) -> Unit)
    fun getFriendRequests(userId: String, callback: (List<String>) -> Unit)
    fun searchUserByEmail(email: String, callback: (User?) -> Unit)
    fun declineFriendRequest(currentUserId: String, senderId: String)
}
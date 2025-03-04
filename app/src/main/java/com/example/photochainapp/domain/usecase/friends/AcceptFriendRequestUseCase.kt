package com.example.photochainapp.domain.usecase.friends

import com.example.photochainapp.domain.repository.FriendsRepository
import javax.inject.Inject

class AcceptFriendRequestUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend fun execute(currentUserId: String, senderId: String) {
        repository.acceptFriendRequest(currentUserId, senderId)
    }
}
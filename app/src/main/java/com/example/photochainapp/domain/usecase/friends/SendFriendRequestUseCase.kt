package com.example.photochainapp.domain.usecase.friends

import com.example.photochainapp.domain.repository.FriendsRepository
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend fun execute(currentUserId: String, targetUserId: String) {
        repository.sendFriendRequest(currentUserId, targetUserId)
    }
}
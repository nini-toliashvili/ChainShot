package com.example.photochainapp.domain.usecase.auth

import com.example.photochainapp.domain.repository.FriendsRepository
import javax.inject.Inject

class DeclineFriendRequestUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend fun execute(currentUserId: String, senderId: String) {
        repository.declineFriendRequest(currentUserId, senderId)
    }
}
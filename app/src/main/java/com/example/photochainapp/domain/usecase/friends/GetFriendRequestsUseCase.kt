package com.example.photochainapp.domain.usecase.friends

import com.example.photochainapp.domain.repository.FriendsRepository
import javax.inject.Inject

class GetFriendRequestsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    fun execute(userId: String, callback: (List<String>) -> Unit) {
        repository.getFriendRequests(userId, callback)
    }
}
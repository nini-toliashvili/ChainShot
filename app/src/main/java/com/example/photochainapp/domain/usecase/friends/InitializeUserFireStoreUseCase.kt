package com.example.photochainapp.domain.usecase.friends

import com.example.photochainapp.domain.repository.FriendsRepository
import javax.inject.Inject

class InitializeUserFireStoreUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    fun execute(userId: String, name: String, email: String) {
        repository.initializeUserFirestore(userId, name, email)
    }
}
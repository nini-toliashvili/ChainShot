package com.example.photochainapp.domain.usecase.friends

import com.example.photochainapp.domain.model.User
import com.example.photochainapp.domain.repository.FriendsRepository
import javax.inject.Inject

class SearchUserByEmailUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    fun execute(email: String, callback: (User?) -> Unit) {
        repository.searchUserByEmail(email, callback)
    }
}
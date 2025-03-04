package com.example.photochainapp.domain.usecase.auth

import com.example.photochainapp.Resource
import com.example.photochainapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(idToken: String) : Resource<FirebaseUser?> {
        return authRepository.signInWithGoogle(idToken)
    }
}
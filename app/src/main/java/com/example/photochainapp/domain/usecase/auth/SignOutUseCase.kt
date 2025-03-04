package com.example.photochainapp.domain.usecase.auth

import android.content.Context
import com.example.photochainapp.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(context: Context, googleSignInOptions: GoogleSignInOptions) {
        authRepository.signOut(context, googleSignInOptions)
    }
}
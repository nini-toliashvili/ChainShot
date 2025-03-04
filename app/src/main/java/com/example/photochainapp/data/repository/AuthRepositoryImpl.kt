package com.example.photochainapp.data.repository

import android.content.Context
import android.util.Log
import com.example.photochainapp.Resource
import com.example.photochainapp.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
        private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithGoogle(idToken: String): Resource<FirebaseUser?> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            Resource.Success(authResult.user)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Sign-in failed")
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun signOut(context: Context, googleSignInOptions: GoogleSignInOptions) {
        try {


            Log.d("AuthRepository", "Signing out from Firebase...")
            auth.signOut()
            Log.d("AuthRepository", "Signing out from Google...")
            val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
            googleSignInClient.signOut().await()
            Log.d("AuthRepository", "Sign out successful")
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error during Google sign-out: ${e.localizedMessage}")
        }
    }

}
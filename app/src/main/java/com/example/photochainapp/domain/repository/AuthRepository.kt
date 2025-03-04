package com.example.photochainapp.domain.repository


import android.content.Context
import com.example.photochainapp.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInWithGoogle(idToken : String) : Resource<FirebaseUser?>
    fun getCurrentUser() : FirebaseUser?
    suspend fun signOut(context: Context, googleSignInOptions: GoogleSignInOptions)

}
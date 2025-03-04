package com.example.photochainapp.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photochainapp.Resource
import com.example.photochainapp.domain.model.User
import com.example.photochainapp.domain.usecase.auth.GetCurrentUserUseCase
import com.example.photochainapp.domain.usecase.auth.SignInWithGoogleUseCase
import com.example.photochainapp.domain.usecase.auth.SignOutUseCase
import com.example.photochainapp.domain.usecase.friends.InitializeUserFireStoreUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val initializeUserFireStoreUseCase: InitializeUserFireStoreUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    private val _googleSignInOptions = MutableStateFlow<GoogleSignInOptions?>(null)
    val googleSignInOptions: StateFlow<GoogleSignInOptions?> = _googleSignInOptions.asStateFlow()

    fun setGoogleSignInOptions(options: GoogleSignInOptions) {
        Log.d("AuthViewModel", "Setting GoogleSignInOptions: $options")
        viewModelScope.launch {
            _googleSignInOptions.value = options
            Log.d("AuthViewModel", "Setting GoogleSignInOptions: $options")
        }
    }

    private val _authState = MutableStateFlow<Resource<FirebaseUser?>>(Resource.Loading)
    val authState: StateFlow<Resource<FirebaseUser?>> = _authState.asStateFlow()

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = signInWithGoogleUseCase(idToken)
            val result = signInWithGoogleUseCase(idToken)
            if (result is Resource.Success) {
                val user = result.data
                val userId = user?.uid ?: ""
                val userName = user?.displayName ?: "Unknown"
                val userEmail = user?.email ?: ""

                if (userId.isNotEmpty()) {
                    initializeUserFireStoreUseCase.execute(userId, userName, userEmail)
                }
                _userData.value = User(userId, userName, userEmail)
            }
        }
    }


    fun getCurrentUser(): FirebaseUser? {
        return getCurrentUserUseCase()
    }

    fun signOut(context: Context, googleSignInOptions: GoogleSignInOptions) {

        viewModelScope.launch {

            Log.d("AuthViewModel", "Signing out...")
            signOutUseCase.invoke(context, googleSignInOptions)
            Log.d("AuthViewModel", "Sign out successful")

            _authState.value = Resource.Success(null)

        }
    }
}
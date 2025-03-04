//package com.example.photochainapp.presentation.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.photochainapp.domain.model.User
//import com.example.photochainapp.domain.usecase.friends.AcceptFriendRequestUseCase
//import com.example.photochainapp.domain.usecase.friends.GetFriendRequestsUseCase
//import com.example.photochainapp.domain.usecase.friends.GetFriendsUseCase
//import com.example.photochainapp.domain.usecase.friends.SendFriendRequestUseCase
//import com.google.firebase.firestore.FirebaseFirestore
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asSharedFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import javax.inject.Inject
//
//@HiltViewModel
//class FriendsViewModel @Inject constructor(
//    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
//    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
//    private val getFriendsUseCase: GetFriendsUseCase,
//    private val getFriendRequestsUseCase: GetFriendRequestsUseCase
//) :ViewModel(){
//
//    private val _friendsList = MutableStateFlow<List<User>>(emptyList())
//    val friendsList: StateFlow<List<User>> = _friendsList.asStateFlow()
//
//
//    private val _friendRequests = MutableStateFlow<List<String>>(emptyList())
//    val friendRequests: StateFlow<List<String>> = _friendRequests.asStateFlow()
//
//
//    private val _eventFlow = MutableSharedFlow<String>()
//    val eventFlow = _eventFlow.asSharedFlow()
//
//    fun sendFriendRequest(currentUserId: String, targetUserId: String) {
//        viewModelScope.launch {
//            sendFriendRequestUseCase.execute(currentUserId, targetUserId)
//            _eventFlow.emit("Friend request sent!")
//        }
//    }
//
//    fun acceptFriendRequest(currentUserId: String, senderId: String) {
//        viewModelScope.launch {
//            acceptFriendRequestUseCase.execute(currentUserId, senderId)
//            _eventFlow.emit("Friend request accepted!")
//        }
//    }
//
//    fun loadFriends(userId: String) {
//        viewModelScope.launch {
//            getUserById(userId).let {
//                getFriendsUseCase.execute(getUserById(userId)?.id!!) {}
//            } ?:
//        }
//    }
//
//    private suspend fun getUserById(userId: String): User? {
//        return try {
//
//            val document = FirebaseFirestore.getInstance().collection("users").document(userId).get().await()
//            if (document.exists()) {
//                User(
//                    id = document.id,
//                    name = document.getString("name") ?: "",
//                    email = document.getString("email") ?: ""
//                )
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            null
//        }
//    }
//
//    fun loadFriendRequests(userId: String) {
//        getFriendRequestsUseCase.execute(userId) { requests ->
//            _friendRequests.value = requests
//        }
//    }
//}
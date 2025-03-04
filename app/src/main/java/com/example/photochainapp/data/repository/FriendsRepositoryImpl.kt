package com.example.photochainapp.data.repository

import android.util.Log
import com.example.photochainapp.domain.model.User
import com.example.photochainapp.domain.repository.FriendsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): FriendsRepository {
    override fun initializeUserFirestore(userId: String, name: String, email: String) {
        val userData = hashMapOf("createdAt" to System.currentTimeMillis(),
            "name" to name,
            "email" to email
        )

        db.collection("users").document(userId)
            .set(userData, SetOptions.merge())
            .addOnSuccessListener { Log.d("Firestore", "User Initialized with name and email") }
            .addOnFailureListener { e -> Log.e("Firestore", "Error initializing user", e)  }
    }

    override fun sendFriendRequest(currentUserId: String, targetUserId: String) {
        val requestData = mapOf(
            "senderId" to currentUserId,
            "status" to "pending"
        )

        db.collection("users").document(targetUserId)
            .collection("friend_requests").document(currentUserId)
            .set(requestData)
    }

    override fun acceptFriendRequest(currentUserId: String, senderId: String) {
        val friendData = mapOf("status" to "accepted")

        db.collection("users").document(currentUserId)
            .collection("friends").document(senderId)
            .set(friendData)

        db.collection("users").document(senderId)
            .collection("friends").document(currentUserId)
            .set(friendData)

        db.collection("users").document(currentUserId)
            .collection("friend_requests").document(senderId)
            .delete()
    }

    override fun getFriends(userId: String, callback: (List<String>) -> Unit) {
        db.collection("users").document(userId)
            .collection("friends")
            .get()
            .addOnSuccessListener { result ->
                val friends = result.documents.map { it.id }
                callback(friends)
            }
    }

    override fun getFriendRequests(userId: String, callback: (List<String>) -> Unit) {
        db.collection("users").document(userId)
            .collection("friend_requests")
            .get()
            .addOnSuccessListener { result ->
                val requests = result.documents.map { it.id }
                callback(requests)
            }
    }

    override fun searchUserByEmail(email: String, callback: (User?) -> Unit) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val userDoc = result.documents[0]
                    val user = User(
                        id = userDoc.id,
                        name = userDoc.getString("name") ?: "",
                        email = userDoc.getString("email") ?: ""
                    )
                    callback(user)
                } else {
                    callback(null) // No user found
                }
            }
            .addOnFailureListener {
                callback(null) // Handle error gracefully
            }
    }


    override fun declineFriendRequest(currentUserId: String, senderId: String) {
        db.collection("users").document(currentUserId)
            .collection("friend_requests").document(senderId)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Friend request declined!")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error declining friend request", e)
            }
    }

}
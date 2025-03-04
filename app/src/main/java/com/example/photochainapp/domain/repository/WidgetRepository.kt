package com.example.photochainapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface WidgetRepository {
    suspend fun saveImageUri(uri: String)
    fun getImageUri() : Flow<String?>
}
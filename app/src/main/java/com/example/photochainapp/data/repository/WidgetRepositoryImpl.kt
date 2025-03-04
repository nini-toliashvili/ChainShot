package com.example.photochainapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

import androidx.datastore.preferences.preferencesDataStore
import com.example.photochainapp.data.repository.WidgetRepositoryImpl.PreferencesKeys.IMAGE_URI_KEY
import com.example.photochainapp.domain.repository.WidgetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.datastore  by preferencesDataStore(name = "widget_prefs")
@Singleton
class WidgetRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    WidgetRepository {


    private object PreferencesKeys {
        val IMAGE_URI_KEY = stringPreferencesKey("image_uri")
    }
    override suspend fun saveImageUri(uri: String) {
        context.datastore.edit { preferences ->
            preferences[IMAGE_URI_KEY] = uri
        }

    }

    override fun getImageUri(): Flow<String?> {
        return context.datastore.data.map { preferences ->
            preferences[IMAGE_URI_KEY]
        }
    }
}
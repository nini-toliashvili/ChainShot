package com.example.photochainapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photochainapp.domain.usecase.widget.GetWidgetImageUseCase
import com.example.photochainapp.domain.usecase.widget.SaveWidgetImageUseCase
import com.example.photochainapp.presentation.widget.WidgetUpdateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getWidgetImageUseCase: GetWidgetImageUseCase,
    private val saveWidgetImageUseCase: SaveWidgetImageUseCase
) : ViewModel() {

    fun saveImageAndUpdateWidget(context: Context, uri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveWidgetImageUseCase(uri) // Save image to DataStore
            withContext(Dispatchers.Main) {
                WidgetUpdateHelper.notifyWidgetUpdate(context) // Notify widget update
            }
        }
    }
}
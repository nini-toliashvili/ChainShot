package com.example.photochainapp.domain.usecase.widget

import com.example.photochainapp.domain.repository.WidgetRepository
import javax.inject.Inject

class SaveWidgetImageUseCase @Inject constructor(private val repository: WidgetRepository) {
    suspend operator fun invoke(uri: String) = repository.saveImageUri(uri)
}
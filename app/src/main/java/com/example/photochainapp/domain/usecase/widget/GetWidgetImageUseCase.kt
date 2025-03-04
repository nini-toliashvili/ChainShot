package com.example.photochainapp.domain.usecase.widget

import com.example.photochainapp.domain.repository.WidgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWidgetImageUseCase @Inject constructor(private val repository: WidgetRepository) {
    operator fun invoke(): Flow<String?> = repository.getImageUri()
}
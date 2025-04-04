package com.example.imageviewapp.domain.usecase

import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(): Flow<List<Photo>> {
        return repository.getFavorites()
    }
}
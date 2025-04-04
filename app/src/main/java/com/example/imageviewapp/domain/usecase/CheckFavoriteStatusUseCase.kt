package com.example.imageviewapp.domain.usecase

import com.example.imageviewapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckFavoriteStatusUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(id: String): Flow<Boolean> {
        return repository.isFavorite(id)
    }
}
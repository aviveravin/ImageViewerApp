package com.example.imageviewapp.domain.usecase

import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.repository.ImageRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(photo: Photo, isFavorite: Boolean) {
        if (isFavorite) {
            repository.removeFavorite(photo.id)
        } else {
            repository.addFavorite(photo)
        }
    }
}
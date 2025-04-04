package com.example.imageviewapp.domain.usecase

import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.repository.ImageRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(page: Int, perPage: Int = 20): List<Photo> {
        return repository.getPhotos(page, perPage)
    }
}
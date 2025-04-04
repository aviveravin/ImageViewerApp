package com.example.imageviewapp.domain.usecase

import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.repository.ImageRepository
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(query: String, page: Int, perPage: Int = 20): List<Photo> {
        return repository.searchPhotos(query, page, perPage)
    }
}
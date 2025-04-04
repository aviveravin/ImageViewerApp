package com.example.imageviewapp.domain.usecase

import com.example.imageviewapp.domain.model.PhotoDetail
import com.example.imageviewapp.domain.repository.ImageRepository
import javax.inject.Inject

class GetPhotoDetailUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(id: String): PhotoDetail {
        return repository.getPhotoById(id)
    }
}
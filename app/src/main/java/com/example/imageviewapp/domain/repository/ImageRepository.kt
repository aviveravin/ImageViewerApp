package com.example.imageviewapp.domain.repository

import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.model.PhotoDetail
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getPhotos(page: Int, perPage: Int): List<Photo>
    suspend fun searchPhotos(query: String, page: Int, perPage: Int): List<Photo>
    suspend fun getPhotoById(id: String): PhotoDetail
    suspend fun addFavorite(photo: Photo)
    suspend fun removeFavorite(id: String)
    fun getFavorites(): Flow<List<Photo>>
    fun isFavorite(id: String): Flow<Boolean>
}
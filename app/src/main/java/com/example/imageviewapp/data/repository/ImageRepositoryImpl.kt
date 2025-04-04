package com.example.imageviewapp.data.repository

import com.example.imageviewapp.data.local.dao.FavoriteDao
import com.example.imageviewapp.data.local.entity.FavoriteEntity
import com.example.imageviewapp.data.remote.api.UnsplashApi
import com.example.imageviewapp.data.remote.dto.PhotoDto
import com.example.imageviewapp.domain.model.Photo
import com.example.imageviewapp.domain.model.PhotoDetail
import com.example.imageviewapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: UnsplashApi,
    private val dao: FavoriteDao
) : ImageRepository {

    override suspend fun getPhotos(page: Int, perPage: Int): List<Photo> {
        return api.getPhotos(page, perPage).map { it.toDomainModel() }
    }

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): List<Photo> {
        return api.searchPhotos(query, page, perPage).results.map { it.toDomainModel() }
    }

    override suspend fun getPhotoById(id: String): PhotoDetail {
        val dto = api.getPhotoById(id)
        return PhotoDetail(
            id = dto.id,
            url = dto.urls.full,
            regularUrl = dto.urls.regular,
            photographerName = dto.user.name,
            username = dto.user.username,
            profileImageUrl = dto.user.profile_image.medium,
            likes = dto.likes,
            description = dto.description,
            createdAt = dto.created_at,
            camera = dto.exif?.model,
            location = dto.location?.country
        )
    }

    override suspend fun addFavorite(photo: Photo) {
        dao.insertFavorite(photo.toEntity())
    }

    override suspend fun removeFavorite(id: String) {
        dao.deleteFavoriteById(id)
    }

    override fun getFavorites(): Flow<List<Photo>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun isFavorite(id: String): Flow<Boolean> {
        return dao.isFavorite(id)
    }

    private fun PhotoDto.toDomainModel(): Photo {
        return Photo(
            id = id,
            url = urls.full,
            regularUrl = urls.regular,
            photographerName = user.name,
            username = user.username,
            profileImageUrl = user.profile_image.medium,
            likes = likes,
            description = description
        )
    }

    private fun Photo.toEntity(): FavoriteEntity {
        return FavoriteEntity(
            id = id,
            photoUrl = url,
            regularUrl = regularUrl,
            photographerName = photographerName,
            username = username,
            profileImageUrl = profileImageUrl
        )
    }

    private fun FavoriteEntity.toDomainModel(): Photo {
        return Photo(
            id = id,
            url = photoUrl,
            regularUrl = regularUrl,
            photographerName = photographerName,
            username = username,
            profileImageUrl = profileImageUrl,
            likes = 0,
            description = null
        )
    }
}
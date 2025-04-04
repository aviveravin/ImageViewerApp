package com.example.imageviewapp.data.remote.api

import com.example.imageviewapp.data.remote.dto.PhotoDetailDto
import com.example.imageviewapp.data.remote.dto.PhotoDto
import com.example.imageviewapp.data.remote.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<PhotoDto>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResultDto

    @GET("photos/{id}")
    suspend fun getPhotoById(@Path("id") id: String): PhotoDetailDto
}
package com.example.imageviewapp.domain.model

data class Photo(
    val id: String,
    val url: String,
    val regularUrl: String,
    val photographerName: String,
    val username: String,
    val profileImageUrl: String,
    val likes: Int,
    val description: String?
)

data class PhotoDetail(
    val id: String,
    val url: String,
    val regularUrl: String,
    val photographerName: String,
    val username: String,
    val profileImageUrl: String,
    val likes: Int,
    val description: String?,
    val createdAt: String,
    val camera: String?,
    val location: String?
)


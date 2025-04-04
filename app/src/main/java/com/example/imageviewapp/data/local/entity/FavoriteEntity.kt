package com.example.imageviewapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val photoUrl: String,
    val regularUrl: String,
    val photographerName: String,
    val username: String,
    val profileImageUrl: String,
    val addedAt: Long = System.currentTimeMillis()
)

package com.example.imageviewapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imageviewapp.data.local.dao.FavoriteDao
import com.example.imageviewapp.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
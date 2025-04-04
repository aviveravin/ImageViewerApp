package com.example.imageviewapp.di

import com.example.imageviewapp.data.local.dao.FavoriteDao
import com.example.imageviewapp.data.remote.api.UnsplashApi
import com.example.imageviewapp.data.repository.ImageRepositoryImpl
import com.example.imageviewapp.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideImageRepository(
        api: UnsplashApi,
        dao: FavoriteDao
    ): ImageRepository {
        return ImageRepositoryImpl(api, dao)
    }
}
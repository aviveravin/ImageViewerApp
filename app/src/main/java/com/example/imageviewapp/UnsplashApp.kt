package com.example.imageviewapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Unsplash app.
 * Annotated with @HiltAndroidApp to trigger Hilt's dependency injection.
 * This makes Hilt generate the necessary components for dependency injection.
 */
@HiltAndroidApp
class UnsplashApp : Application()
package com.aerotune.player.di

import android.content.ContentResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContentResolver(
        resolver: AppContentResolver
    ): ContentResolver = resolver.get()
}

class AppContentResolver @javax.inject.Inject constructor(
    private val resolver: ContentResolver
) {
    fun get(): ContentResolver = resolver
}

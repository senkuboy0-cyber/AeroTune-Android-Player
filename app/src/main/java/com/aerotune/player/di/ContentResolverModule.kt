package com.aerotune.player.di

import android.content.ContentResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentResolverModule {
    @Provides
    @Singleton
    fun provideContentResolver(
        contentResolver: ContentResolver
    ): ContentResolver = contentResolver
}

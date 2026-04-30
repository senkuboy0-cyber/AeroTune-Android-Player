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
        androidContentResolver: AndroidContentResolver
    ): ContentResolver {
        return androidContentResolver.get()
    }
}

class AndroidContentResolver @javax.inject.Inject constructor(
    private val resolver: ContentResolver
) {
    fun get(): ContentResolver = resolver
}

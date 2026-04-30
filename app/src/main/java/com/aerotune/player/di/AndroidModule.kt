package com.aerotune.player

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AndroidModule {

    @Provides
    @Singleton
    fun provideAppContentResolver(
        @ApplicationContext context: Context
    ): ContentResolverProvider {
        return ContentResolverProvider(context.contentResolver)
    }
}

class ContentResolverProvider(
    private val resolver: ContentResolver
) {
    fun get(): ContentResolver = resolver
}

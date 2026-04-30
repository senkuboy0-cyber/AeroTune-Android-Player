package com.aerotune.player

import android.content.ContentResolver
import dagger.hilt.android.AeroTuneApp
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.internal.GeneratedComponentManager
import kotlinx.inject.Provider

class AndroidContentResolver(
    private val contentResolver: ContentResolver
) {
    fun get(): ContentResolver = contentResolver
}

@javax.inject.Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@ javax.inject.Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppContent

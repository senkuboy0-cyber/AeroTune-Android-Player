package com.aerotune.player

import android.content.ContentResolver

class AndroidContentResolver(
    private val contentResolver: ContentResolver
) {
    fun get(): ContentResolver = contentResolver
}

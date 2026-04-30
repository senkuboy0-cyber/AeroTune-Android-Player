package com.aerotune.player.player

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.media3.notification.MediaNotificationProvider
import androidx.media3.media3.session.MediaSession
import androidx.media3.media3.session.MediaSessionService
import com.aerotune.player.ui.MainActivity

class PlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        val player = androidx.media3.exoplayer.ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
        }
        mediaSession = null
        super.onDestroy()
    }
}

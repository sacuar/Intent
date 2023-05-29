package com.example.intent

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector


class MediaPlayerService : Service() {
    private var player: ExoPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Extract the media file URL from the intent extras
        val mediaUrl = intent?.getStringExtra("media_url")

        if (mediaUrl != null) {
            // Call the method to play the media file
            playMedia(mediaUrl)
        }

        // Return START_STICKY to indicate that the service should be restarted if it gets terminated
        return START_STICKY
    }

    private fun playMedia(mediaUrl:String) {
        // Release any existing player instance
        releasePlayer()

        // Create a new player instance
        player = ExoPlayer.Builder(this)
            .setTrackSelector(DefaultTrackSelector(this))
            //.setTrackSelector(DefaultTrackSelector(this))
            .build()

        // Create a media item
        val mediaItem = MediaItem.fromUri(mediaUrl)

        // Create a media source and pass the media item
        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(this)
        )
            .createMediaSource(mediaItem)

        // Prepare the player with the media source
        player?.setMediaSource(mediaSource)
        player?.prepare()

        // Start playback
        player?.playWhenReady = true
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the player when the service is destroyed
        releasePlayer()
    }
}

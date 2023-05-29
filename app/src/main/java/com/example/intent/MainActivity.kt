package com.example.intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var isMediaPlayerServiceRunning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playButton = findViewById<Button>(R.id.startButton)
        val stopButton = findViewById<Button>(R.id.stopButton)

        playButton.setOnClickListener {

            if (!isMediaPlayerServiceRunning) {
                val mediaUrl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4" // Replace with your actual media URL
                val intent = Intent(this@MainActivity, MediaPlayerService::class.java)
                intent.putExtra("media_url", mediaUrl)
                startService(intent)
                isMediaPlayerServiceRunning = true
            }
        }

        stopButton.setOnClickListener {

            if (isMediaPlayerServiceRunning) {
                val intent = Intent(this@MainActivity, MediaPlayerService::class.java)
                stopService(intent)
                isMediaPlayerServiceRunning = false
            }
        }
    }
}



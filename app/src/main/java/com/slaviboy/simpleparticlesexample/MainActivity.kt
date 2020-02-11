package com.slaviboy.simpleparticlesexample

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.slaviboy.simpleparticlesexample.drawing.TextureView

class MainActivity : AppCompatActivity() {

    lateinit var lineParticles: ImageButton
    lateinit var dustParticles: ImageButton
    lateinit var textureView: TextureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hide action and navigation bars
        hideSystemUI()

        textureView = findViewById(R.id.canvas)

        // attach click listener to hide or show line particles
        lineParticles = findViewById(R.id.line_particles)
        lineParticles.setOnClickListener {
            val temp = it as com.slaviboy.simpleparticlesexample.ImageButton
            temp.switchButtonEnabled()
            textureView.lineParticles?.isVisible = temp.isButtonEnabled
        }

        // attach click listener to hide or show dust particles
        dustParticles = findViewById(R.id.dust_particles)
        dustParticles.setOnClickListener {
            val temp = it as com.slaviboy.simpleparticlesexample.ImageButton
            temp.switchButtonEnabled()
            textureView.dustParticles?.isVisible = temp.isButtonEnabled
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}
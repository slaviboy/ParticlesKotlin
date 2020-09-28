package com.slaviboy.simpleparticlesexample

import android.graphics.Color
import android.graphics.RadialGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.slaviboy.simpleparticlesexample.drawing.textureview.TextureView

class MainActivity : AppCompatActivity() {

    lateinit var lineParticles: Button
    lateinit var dustParticles: Button
    lateinit var textureView: TextureView // lateinit var textureView: SurfaceView
    lateinit var clearBackground: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hide action and navigation bars
        hideSystemUI()

        textureView = findViewById(R.id.canvas)
        lineParticles = findViewById(R.id.line_particles)
        dustParticles = findViewById(R.id.dust_particles)
        clearBackground = findViewById(R.id.clear_background)

        // attach click listener to hide or show line particles
        lineParticles.setOnClickListener {
            it.isEnabled = false
            dustParticles.isEnabled = true
            textureView.lineParticlesGenerator.isVisible = !it.isEnabled
            textureView.dustParticlesGenerator.isVisible = it.isEnabled
        }

        // attach click listener to hide or show dust particles
        dustParticles.setOnClickListener {
            it.isEnabled = false
            lineParticles.isEnabled = true
            textureView.lineParticlesGenerator.isVisible = it.isEnabled
            textureView.dustParticlesGenerator.isVisible = !it.isEnabled
        }

        clearBackground.setOnCheckedChangeListener { buttonView, isChecked ->
            textureView.drawingThread?.clearCanvas = isChecked

            val radius = if (isChecked) {
                50.0f
            } else {
                10.0f
            }
            textureView.drawingThread?.dustParticlesGenerator?.particlesRadialGradient = RadialGradient(
                0.0f,
                0.0f,
                radius,
                intArrayOf(Color.BLUE, Color.TRANSPARENT),
                floatArrayOf(0.0f, 0.9f),
                Shader.TileMode.MIRROR
            )
            textureView.drawingThread?.dustParticlesGenerator?.maxRadius = radius.toDouble()
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
package com.slaviboy.simpleparticlesexample.drawing

import android.content.Context
import android.graphics.Color
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener

// Copyright (C) 2020 Stanislav Georgiev
//  https://github.com/slaviboy
//
//	This program is free software: you can redistribute it and/or modify
//	it under the terms of the GNU Affero General Public License as
//	published by the Free Software Foundation, either version 3 of the
//	License, or (at your option) any later version.
//
//	This program is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU Affero General Public License for more details.
//
//	You should have received a copy of the GNU Affero General Public License
//	along with this program.  If not, see <http://www.gnu.org/licenses/>.

/**
 * Texture view that is used for drawing the particles on canvas. The view is using
 * HA(hardware acceleration) for better performance.
 */
class TextureView : TextureView, SurfaceTextureListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var drawingThread: DrawingThread? = null
    var lineParticles: com.slaviboy.particles.line.Particles? = null
    var dustParticles: com.slaviboy.particles.dust.Particles? = null

    init {
        surfaceTextureListener = this
        isOpaque = false
    }

    /**
     * Create the drawing thread, attach the particles and start the thread.
     */
    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {

        createParticles()

        drawingThread = DrawingThread(
            TextureViewHolder(this),
            lineParticles,
            dustParticles
        )

        drawingThread?.isRunning = true
        drawingThread?.start()
    }

    /**
     * Update the view size in the particle properties.
     */
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        dustParticles?.viewWidth = width
        dustParticles?.viewHeight = height

        lineParticles?.viewWidth = width
        lineParticles?.viewHeight = height
    }

    /**
     * Deestroy the drawing thread.
     */
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        var retry = true
        drawingThread?.isRunning = false
        while (retry) {
            try {
                drawingThread?.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

    fun createParticles() {

        lineParticles = com.slaviboy.particles.line.Particles(width, height)
        dustParticles = com.slaviboy.particles.dust.Particles(
            viewWidth = width,
            viewHeight = height,
            maxRadius = 10.0f,
            radialGradient = RadialGradient(
                0.0f,
                0.0f,
                10.0f,
                intArrayOf(Color.BLUE, Color.TRANSPARENT),
                floatArrayOf(0.1f, 1.0f),
                Shader.TileMode.MIRROR
            )
        )
    }
}
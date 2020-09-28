/*
* Copyright (C) 2020 Stanislav Georgiev
* https://github.com/slaviboy
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.slaviboy.simpleparticlesexample.drawing

import android.content.Context
import android.graphics.Color
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import com.slaviboy.particles.dust.DustParticlesGenerator
import com.slaviboy.particles.line.LineParticlesGenerator

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

    var drawingThread: DrawingThread? = null
    var lineParticlesGenerator: LineParticlesGenerator
    var dustParticlesGenerator: DustParticlesGenerator

    init {
        surfaceTextureListener = this
        isOpaque = false

        lineParticlesGenerator = LineParticlesGenerator(width, height)
        dustParticlesGenerator = DustParticlesGenerator(
            viewWidth = width,
            viewHeight = height,
            minRadius = 0.0,
            maxRadius = 30.0,
            particlesRadialGradient = RadialGradient(
                0.0f,
                0.0f,
                30.0f,
                intArrayOf(Color.BLUE, Color.TRANSPARENT),
                floatArrayOf(0.0f, 0.9f),
                Shader.TileMode.MIRROR
            )
        )
    }

    /**
     * Create the drawing thread, attach the particles and start the thread.
     */
    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        updateGeneratorsSize(width, height)
        drawingThread = DrawingThread(
            TextureViewHolder(this),
            lineParticlesGenerator,
            dustParticlesGenerator
        )
        drawingThread?.isRunning = true
        drawingThread?.start()
    }

    /**
     * Update the view size in the particle properties.
     */
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        updateGeneratorsSize(width, height)
    }

    /**
     * Destroy the drawing thread.
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

    /**
     * Update the sizes for the generators
     * @param width the new view width for the generator
     * @param height the new view height for the generator
     */
    fun updateGeneratorsSize(width: Int, height: Int) {

        // set view size for the dust particle generator
        dustParticlesGenerator.viewWidth = width
        dustParticlesGenerator.viewHeight = height

        // set view size for the lines particle generator
        lineParticlesGenerator.viewWidth = width
        lineParticlesGenerator.viewHeight = height
    }
}
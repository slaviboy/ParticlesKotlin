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
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.slaviboy.particles.dust.DustParticlesGenerator
import com.slaviboy.particles.line.LineParticlesGenerator

/**
 * Simple surface view class that has drawing thread, and draws all
 * particles on the canvas.
 */
class SurfaceView : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var drawingThread: DrawingThread? = null
    private var lineParticlesGenerator: LineParticlesGenerator
    private var dustParticlesGenerator: DustParticlesGenerator

    init {
        holder.addCallback(this)

        lineParticlesGenerator = LineParticlesGenerator(width, height)
        dustParticlesGenerator = DustParticlesGenerator(
            viewWidth = width,
            viewHeight = height,
            maxRadius = 10.0,
            particlesRadialGradient = RadialGradient(
                0.0f,
                0.0f,
                10.0f,
                intArrayOf(Color.BLUE, Color.TRANSPARENT),
                floatArrayOf(0.1f, 1.0f),
                Shader.TileMode.MIRROR
            )
        )
    }

    /**
     * Update the view size in the particle properties.
     */
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        // set view size for the dust particle generator
        dustParticlesGenerator.viewWidth = width
        dustParticlesGenerator.viewHeight = height

        // set view size for the lines particle generator
        lineParticlesGenerator.viewWidth = width
        lineParticlesGenerator.viewHeight = height
    }

    /**
     * Create the drawing thread, attach particles and start the thread.
     */
    override fun surfaceCreated(holder: SurfaceHolder) {
        drawingThread = DrawingThread(
            SurfaceViewHolder(holder),
            lineParticlesGenerator,
            dustParticlesGenerator
        )
        drawingThread?.apply {
            isRunning = true
            start()
        }
    }

    /**
     * Destroy the drawing thread.
     */
    override fun surfaceDestroyed(holder: SurfaceHolder) {
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
    }
}
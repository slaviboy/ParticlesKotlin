package com.slaviboy.simpleparticlesexample.drawing

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

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
    private var lineParticles: com.slaviboy.particles.line.Particles? = null
    private var dustParticles:  com.slaviboy.particles.dust.Particles? = null

    init {
        holder.addCallback(this)
    }

    /**
     * Update the view size in the particle properties.
     */
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        dustParticles?.viewWidth = width
        dustParticles?.viewHeight = height

        lineParticles?.viewWidth = width
        lineParticles?.viewHeight = height
    }

    /**
     * Create the drawing thread, attach particles and start the thread.
     */
    override fun surfaceCreated(holder: SurfaceHolder) {
        drawingThread = DrawingThread(
            SurfaceViewHolder(holder)
        )
        drawingThread?.isRunning = true
        drawingThread?.lineParticles = lineParticles
        drawingThread?.dustParticles = dustParticles
        drawingThread?.start()
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
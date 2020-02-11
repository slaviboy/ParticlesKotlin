package com.slaviboy.simpleparticlesexample.drawing

import android.graphics.Canvas

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
 * Simple drawing thread, used for drawing the particles on canvas specified by the
 * surface holder
 */
class DrawingThread(
    var surfaceHolder: ISurfaceHolder,
    var lineParticles: com.slaviboy.particles.line.Particles? = null,
    var dustParticles:  com.slaviboy.particles.dust.Particles? = null
) : Thread() {

    var isRunning = false
    private var previousTime: Long
    private val fps = 60

    /**
     * Runnable method for the thread
     */
    override fun run() {
        var canvas: Canvas?
        while (isRunning) {

            val currentTimeMillis = System.currentTimeMillis()
            val elapsedTimeMs = currentTimeMillis - previousTime
            val sleepTimeMs = (1000f / fps - elapsedTimeMs).toLong()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()

                // sleep the thread if frame rate is bigger than the fps
                if (canvas == null) {
                    sleep(1)
                    continue
                } else if (sleepTimeMs > 0) {
                    sleep(sleepTimeMs)
                }

                // draw the particles
                synchronized(surfaceHolder) {
                    lineParticles?.drawOn(canvas, true)
                    dustParticles?.drawOn(canvas, false)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                    previousTime = System.currentTimeMillis()
                }
            }
        }
    }

    init {
        previousTime = System.currentTimeMillis()
    }
}
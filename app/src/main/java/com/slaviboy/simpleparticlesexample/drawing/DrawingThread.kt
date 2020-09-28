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

import android.graphics.Canvas
import android.graphics.Color
import com.slaviboy.particles.dust.DustParticlesGenerator
import com.slaviboy.particles.line.LineParticlesGenerator
import com.slaviboy.simpleparticlesexample.drawing.surfaceview.Holder

/**
 * Simple drawing thread, used for drawing the particles on canvas specified by the
 * surface holder
 * @param surfaceHolder surface holder with the canvas
 * @param lineParticlesGenerator generator for the line particles
 * @param dustParticlesGenerator generator for the dust particles
 */
class DrawingThread(
    var surfaceHolder: Holder,
    var lineParticlesGenerator: LineParticlesGenerator,
    var dustParticlesGenerator: DustParticlesGenerator
) : Thread() {

    var isRunning: Boolean = false
    var previousTime: Long = 0L
    val fps: Int = 60
    var clearCanvas: Boolean = true
        set(value) {

            // clear the bitmaps for both generators
            lineParticlesGenerator.clearBitmap()
            dustParticlesGenerator.clearBitmap()
            field = value
        }

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

                    // clear canvas
                    canvas.drawColor(Color.WHITE )

                    when {
                        lineParticlesGenerator.isVisible -> {
                            lineParticlesGenerator.update()
                            lineParticlesGenerator.draw(canvas = canvas, clearCanvas = clearCanvas)
                        }
                        dustParticlesGenerator.isVisible -> {
                            dustParticlesGenerator.update()
                            dustParticlesGenerator.draw(canvas = canvas, clearCanvas = clearCanvas)
                        }
                    }
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
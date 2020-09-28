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
package com.slaviboy.particles.line

import android.graphics.*
import com.slaviboy.particles.main.ParticleGenerator

/**
 * Simple particle class, that represent particles, that are connected with line, depending if the
 * distance between the points matches the minimum distance.
 * @param viewWidth view with
 * @param viewHeight view height
 * @param minDistance minimum radius between particles, it determines the opacity of the line between any two particles
 * @param maxDistance maximum radius between particles, it determines if line should be drawn between any two particles
 * @param numberParticles number of particles
 * @param particlesColor fill color for the particles
 * @param particlesRadius radius for all particles
 * @param linesColor stroke color for the line
 * @param linesWidth stroke width for the lines
 * @param isVisible if particles and lines from the particle generator should be drawn
 */
class LineParticlesGenerator(
    viewWidth: Int = 0,
    viewHeight: Int = 0,
    numberParticles: Int = 200,
    particlesColor: Int = Color.BLACK,
    particlesSpeed: Double = 5.0,
    isVisible: Boolean = true,
    var minDistance: Double = 40.0,
    maxDistance: Double = 160.0,
    var particlesRadius: Double = 2.5,
    var linesColor: Int = Color.BLACK,
    var linesWidth: Double = 1.0
) : ParticleGenerator(viewWidth, viewHeight, numberParticles, particlesColor, particlesSpeed, isVisible) {

    internal var minDistance2: Double = minDistance * minDistance   // minimum distance power of two
    internal var maxDistance2: Double = maxDistance * maxDistance   // maximum distance power of two

    internal var particles: Array<LineParticle> = Array(numberParticles) {

        // create particle with random position inside the view
        LineParticle(
            x = Math.random() * viewWidth,
            y = Math.random() * viewHeight,
            vx = 0.0,
            vy = 0.0,
            radius = particlesRadius,
            speed = particlesSpeed
        )
    }


    var maxDistance: Double = maxDistance
        set(value) {
            particles.forEach {
                it.maxDistance = value
            }
            field = value
        }

    /**
     * Method for updating the position of all particles
     */
    override fun update() {
        particles.forEach {
            it.update(viewWidth.toDouble(), viewHeight.toDouble())
        }
    }

    /**
     * Method for drawing the particles on given canvas
     */
    override fun draw(canvas: Canvas, paint: Paint, clearCanvas: Boolean) {
        if (!isVisible) {
            return
        }

        val drawCanvas = if (clearCanvas) {
            // use the canvas passed in the method
            canvas
        } else {

            // check if update of the bitmap is needed
            if (updateBitmap) {
                updateBitmap = false
                bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
                bitmapCanvas = Canvas(bitmap)
            }

            // use the canvas for the bitmap, without clearing the previous
            bitmapCanvas
        }

        synchronized(paint) {

            // draw particles
            paint.apply {
                style = Paint.Style.FILL
                color = particlesColor
            }
            particles.forEach {
                it.draw(drawCanvas, paint)
            }

            // draw lines between each two particles
            paint.apply {
                style = Paint.Style.STROKE
                color = linesColor
                strokeWidth = linesWidth.toFloat()
            }
            for (i: Int in 0 until numberParticles) {
                for (j: Int in (i + 1) until numberParticles) {

                    val pi = particles[i]
                    val pj = particles[j]

                    // get the distance between the two particles, it is power of two
                    val dx = pi.x - pj.x
                    val dy = pi.y - pj.y
                    val d2 = dx * dx + dy * dy

                    if (d2 < maxDistance2) {

                        // set color and transparency for the lines
                        paint.apply {
                            color = linesColor
                            alpha = if (d2 > minDistance2) {
                                (((maxDistance2 - d2) / (maxDistance2 - minDistance2)) * 255).toInt()
                            } else {
                                1
                            }
                        }

                        drawCanvas.drawLine(pi.x.toFloat(), pi.y.toFloat(), pj.x.toFloat(), pj.y.toFloat(), paint)
                    }
                }
            }
            paint.alpha = 255
        }

        // draw everything on the bitmap if clearing is disabled
        if (!clearCanvas) {
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
        }
    }

}
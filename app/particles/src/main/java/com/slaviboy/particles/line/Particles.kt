package com.slaviboy.particles.line

import android.graphics.*
import com.slaviboy.particles.dust.Particles

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
 * Simple particle class, that represent particles, that are connected with line, depending if the
 * distance between the points matches the minimum distance.
 *
 * @param viewWidth view with
 * @param viewHeight view height
 * @param radius particles radius
 * @param minDistance minimum radius between particles
 * @param maxDistance maximum radius between particles
 * @param numberParticles number of particles
 * @param particleColor integer representation of the color for the particles
 * @param lineColor integer representation of the color fot the lines
 * @param hasRandomColor whether random color should be choose for each particle and line
 */
class Particles(
    var viewWidth: Int = 0,
    var viewHeight: Int = 0,
    var radius: Float = 2.5f,
    var minDistance: Float = 40f,
    var maxDistance: Float = 60f,
    var numberParticles: Int = 100,
    var particleColor: Int = Color.BLACK,
    var lineColor: Int = Color.BLACK,
    var hasRandomColor: Boolean = false,
    var isVisible: Boolean = true
) {

    private var minDistance2 = minDistance * minDistance   // minimum distance power of two
    private var maxDistance2 = maxDistance * maxDistance   // maximum distance power of two

    private var particles = arrayOfNulls<Particle>(numberParticles)
    private val paint: Paint = Paint()
    private var clearPaint = Paint()

    init {
        paint.style = Paint.Style.FILL
        paint.color = particleColor
        paint.isAntiAlias = true

        for (i: Int in 0 until numberParticles) {

            val colorTemp = if (hasRandomColor) {
                Particles.randomColor()
            } else {
                particleColor
            }

            particles[i] = Particle(
                x = (Math.random() * viewWidth).toFloat(),
                y = (Math.random() * viewHeight).toFloat(),
                vx = 0.0f,
                vy = 0.0f,
                radius = radius,
                color = colorTemp
            )
        }

        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun drawOn(canvas: Canvas, clearCanvas: Boolean = false) {

        synchronized(paint) {
            if (clearCanvas) {
                canvas.drawPaint(clearPaint)
            }

            if (!isVisible) {
                return
            }

            // draw particles
            paint.style = Paint.Style.FILL
            for (i: Int in 0 until numberParticles) {
                particles[i]?.draw(canvas, paint, maxDistance)
            }

            paint.style = Paint.Style.STROKE
            paint.color = lineColor
            for (i: Int in 0 until numberParticles) {
                for (j: Int in (i + 1) until numberParticles) {

                    val pi = particles[i]
                    val pj = particles[j]
                    if (pi != null && pj != null) {
                        val dx = pi.x - pj.x
                        val dy = pi.y - pj.y
                        val d2 = dx * dx + dy * dy

                        if (d2 < maxDistance2) {

                            val colorTemp = if (hasRandomColor) {
                                Particles.randomColor()
                            } else {
                                lineColor
                            }
                            paint.color = colorTemp

                            paint.alpha = if (d2 > minDistance2) {
                                (((maxDistance2 - d2) / (maxDistance2 - minDistance2)) * 255).toInt()
                            } else {
                                1
                            }

                            canvas.drawLine(pi.x, pi.y, pj.x, pj.y, paint)
                        }
                    }
                }
            }

            paint.alpha = 255
        }
    }
}
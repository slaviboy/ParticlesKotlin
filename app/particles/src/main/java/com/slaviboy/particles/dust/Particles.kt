package com.slaviboy.particles.dust

import android.graphics.*
import java.util.*

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
 * Simple class for creating dust particles, with fully customizable properties.
 *
 * @param viewWidth view with
 * @param viewHeight view height
 * @param minRadius minimum allowed particles radius
 * @param maxRadius maximum allowed particles radius
 * @param numberParticles total number of particles
 * @param color particles solid color
 * @param radialGradient if particles have radial gradient
 * @param hasRandomColor if particles will have random solid color
 */
class Particles(
    var viewWidth: Int = 0,
    var viewHeight: Int = 0,
    var minRadius: Float = 1.0f,
    var maxRadius: Float = 16.0f,
    var numberParticles: Int = 50,
    var color: Int = Color.BLACK,
    var radialGradient: RadialGradient? = null,
    var hasRandomColor: Boolean = false,
    var isVisible:Boolean = true
) {

    companion object {

        /**
         * Generate random integer representation of a color
         */
        fun randomColor(): Int {
            return Color.rgb(
                (Math.random() * 256).toInt(),
                (Math.random() * 256).toInt(),
                (Math.random() * 256).toInt()
            )
        }

        /**
         * Generate random number in integer range
         * example: [12, 65]
         */
        fun rand(min: Int, max: Int): Float {
            return (Random().nextInt(max - min + 1) + min).toFloat()
        }

        /**
         * Generate random number in float range
         * example: [0.3f, 0.9f]
         */
        fun rand(min: Float, max: Float): Float {
            return (min + (max - min) * Random().nextDouble()).toFloat()
        }
    }

    private var particles = arrayOfNulls<Particle>(numberParticles)
    private val paint: Paint = Paint()
    private var clearPaint = Paint()

    init {
        paint.style = Paint.Style.FILL
        paint.color = color
        if (radialGradient != null) {
            paint.shader = radialGradient
        }
        paint.isAntiAlias = true

        for (i: Int in 0 until numberParticles) {
            val colorTemp = if (hasRandomColor) {
                randomColor()
            } else {
                color
            }
            particles[i] = Particle(
                x = (Math.random() * viewWidth).toFloat(),
                y = (Math.random() * viewHeight).toFloat(),
                vx = 0.0f,
                vy = 0.0f,
                scaleFact = rand((minRadius / maxRadius), 1.0f),
                opacityFact = (Math.random() / 50).toFloat(),
                color = colorTemp
            )
        }

        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }


    /**
     * Method that draw all the particles on given canvas.
     */
    fun drawOn(canvas: Canvas, clearCanvas: Boolean = false) {

        synchronized(paint) {
            if (clearCanvas) {
                canvas.drawPaint(clearPaint)
            }

            if (!isVisible) {
                return
            }

            // change particles position and draw them
            paint.style = Paint.Style.FILL
            for (i: Int in 0 until numberParticles) {
                particles[i]?.draw(canvas, paint, minRadius, maxRadius)
            }

            paint.alpha = 255
        }

    }
}
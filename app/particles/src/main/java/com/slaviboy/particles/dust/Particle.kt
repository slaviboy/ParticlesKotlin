package com.slaviboy.particles.dust

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

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
 * Simple dust particle class, that consist of properties particular for the
 * dust particles.
 *
 * @param x center x coordinate of the particle
 * @param y center y coordinate of the particle
 * @param vx velocity in the horizontal direction
 * @param vy velocity in the vertical direction
 * @param scaleFact determines the particle radius, by multiplying this value with tha maximum allowed radius
 * @param opacity current particle opacity
 * @param opacityFact show how fat the opacity of a particle is change, increasing or decreasing
 * @param isOpacityIncreased shows if opacity is currently being increasing or decreasing
 * @param color current particle solid color
 */
class Particle(
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var vx: Float = 0.0f,
    var vy: Float = 0.0f,
    var scaleFact: Float = 0.5f,
    var opacity: Float = 1.0f,
    var opacityFact: Float = 0.01f,
    var isOpacityIncreased: Boolean = false,
    var color: Int = Color.BLACK
) {

    /**
     * Draw particle on given canvas
     *
     * @param canvas canvas where the particle should be drawn
     * @param paint paint object holding info about the drawing
     * @param minRadius minimum allowed radius for the particles
     * @param maxRadius maximum allowed radius for the particles
     */
    fun draw(canvas: Canvas, paint: Paint, minRadius: Float, maxRadius: Float) {

        val viewWidth = canvas.width
        val viewHeight = canvas.height

        // set color before alpha, otherwise alpha will not be applied
        paint.color = color
        paint.alpha = (opacity * 255).toInt()

        // translate to particle position and scale to match particles size
        canvas.save()
        canvas.translate(x, y)
        canvas.scale(scaleFact, scaleFact)

        // change opacity 0 -> 1 -> 0...
        if (isOpacityIncreased) {
            opacity += opacityFact

            if (opacity >= 1) {
                opacity = 1.0f
                isOpacityIncreased = false
                opacityFact = (Math.random() / 50).toFloat()
            }
        } else {
            opacity -= opacityFact
            if (opacity <= 0) {
                opacity = 0.0f
                isOpacityIncreased = true
                opacityFact = (Math.random() / 50).toFloat()
            }
        }

        // update particle position
        x += vx
        y += vy

        // if particles move outside the view, reset position and opacity
        val radius = maxRadius * scaleFact
        if (x < -radius || x > viewWidth + radius || y < -radius || y > viewHeight + radius) {
            x = (Math.random() * viewWidth).toFloat()
            y = (Math.random() * viewHeight).toFloat()
            opacity = 0.0f
            scaleFact = Particles.rand((minRadius / maxRadius), 1.0f)
        }

        // update velocity
        vx += 0.2f * (Math.random() - 0.5f).toFloat() - 0.01f * vx
        vy += 0.2f * (Math.random() - 0.5f).toFloat() - 0.01f * vy

        // draw circle
        canvas.drawCircle(0.0f, 0.0f, maxRadius, paint)
        canvas.restore()
    }

}
package com.slaviboy.particles.line

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
 * Simple line particle class, that consist of properties particular for the
 * line particles.
 *
 * @param x center x coordinate of the particle
 * @param y center y coordinate of the particle
 * @param vx velocity in the horizontal direction
 * @param vy velocity in the vertical direction
 * @param radius particle radius
 * @param color current particle solid color
 */
class Particle(
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var vx: Float = 0.0f,
    var vy: Float = 0.0f,
    var radius: Float = 10.0f,
    var color: Int = Color.BLACK
) {

    /**
     * Draw particle on given canvas
     *
     * @param canvas canvas where the particle should be drawn
     * @param paint paint object holding info about the drawing
     * @param maxDistance maximum distance between particles
     */
    fun draw(canvas: Canvas, paint: Paint, maxDistance: Float) {

        paint.color = color

        val viewWidth = canvas.width
        val viewHeight = canvas.height

        // set new position
        x += vx
        y += vy

        if (x < -radius || x > viewWidth + radius || y < -radius || y > viewHeight + radius) {
            x = (Math.random() * viewWidth).toFloat()
            y = (Math.random() * viewHeight).toFloat()
        }

        // make sure particle is in border
        if (x < -maxDistance) {
            x += viewWidth + maxDistance * 2
        } else if (x > viewWidth + maxDistance) {
            x -= viewWidth + maxDistance * 2
        }

        if (y < -maxDistance) {
            y += viewHeight + maxDistance * 2
        } else if (y > viewHeight + maxDistance) {
            y -= viewHeight + maxDistance * 2
        }

        // update velocity
        vx += 0.2f * (Math.random() - 0.5f).toFloat() - 0.01f * vx
        vy += 0.2f * (Math.random() - 0.5f).toFloat() - 0.01f * vy

        // draw particle on canvas
        canvas.drawCircle(x, y, radius, paint)
    }
}
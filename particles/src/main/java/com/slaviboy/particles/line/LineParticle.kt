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

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Simple line particle class, that consist of properties particular for the
 * line particles.
 * @param x center x coordinate of the particle
 * @param y center y coordinate of the particle
 * @param vx velocity in the horizontal direction
 * @param vy velocity in the vertical direction
 * @param radius particle radius
 * @param speed speed of the particle
 */
class LineParticle(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var vx: Double = 0.0,
    var vy: Double = 0.0,
    var radius: Double = 10.0,
    var speed: Double = 1.0,
    var maxDistance: Double = 100.0
) {

    /**
     * Update the position of the particle by moving in to a random direction
     * @param viewWidth with of the view
     * @param viewHeight height of the view
     * @param maxDistance maximum allowed distance between particles
     */
    fun update(viewWidth: Double, viewHeight: Double) {

        // set new position for the particle using the velocity values
        x += vx * speed
        y += vy * speed

        // in case the particle goes outside the view area, set new random position
        if (x < -radius || x > viewWidth + radius || y < -radius || y > viewHeight + radius) {
            x = (Math.random() * viewWidth)
            y = (Math.random() * viewHeight)
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

        // update velocity, it determines the change of direction for the particle
        vx += 0.2 * (Math.random() - 0.5) - 0.01 * vx
        vy += 0.2 * (Math.random() - 0.5) - 0.01 * vy
    }

    /**
     * Draw particle on given canvas
     * @param canvas canvas where the particle should be drawn
     * @param paint paint object holding info about the drawing
     */
    fun draw(canvas: Canvas, paint: Paint) {

        // draw particle on canvas
        canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paint)
    }
}
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
package com.slaviboy.particles.dust

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.slaviboy.particles.main.Particle
import com.slaviboy.particles.main.ParticleGenerator

/**
 * Simple dust particle class, that consist of properties particular for the
 * dust particles.
 * @param x center x coordinate of the particle
 * @param y center y coordinate of the particle
 * @param vx velocity in the horizontal direction
 * @param vy velocity in the vertical direction
 * @param scaleFact by how much to increase/decrease the scale
 * @param scale determines the particle radius, by multiplying this value with tha maximum allowed radius
 * @param opacity current particle opacity
 * @param opacityFact by how much to increase/decrease the opacity
 * @param isOpacityIncreased shows if opacity is currently being increasing or decreasing
 * @param color current particle solid color
 * @param minRadius minimum allowed radius of a particle
 * @param maxRadius maximum allowed radius of a particle
 */
class DustParticle(
    x: Double = 0.0,
    y: Double = 0.0,
    vx: Double = 0.0,
    vy: Double = 0.0,
    speed: Double = 5.0,
    var scaleFact: Double = 0.01,
    var scale: Double = Math.random(),
    var opacity: Double = Math.random(),
    var opacityFact: Double = 0.01,
    var isOpacityIncreased: Boolean = false,
    var isSizeIncreased: Boolean = false,
    var color: Int = Color.BLACK,
    var minRadius: Double = 10.0,
    var maxRadius: Double = 50.0
) : Particle(x, y, vx, vy, speed) {

    /**
     * Update the position of the particle by moving in to a random direction
     * @param viewWidth with of the view
     * @param viewHeight height of the view
     */
    override fun update(viewWidth: Double, viewHeight: Double) {

        // update particle position
        x += vx * speed
        y += vy * speed

        // if particles move outside the view, reset position and opacity
        val radius = maxRadius * scale
        if (x < -radius || x > viewWidth + radius || y < -radius || y > viewHeight + radius) {
            x = Math.random() * viewWidth
            y = Math.random() * viewHeight
            opacity = 0.0
            scale = ParticleGenerator.randomDouble(minRadius / maxRadius, 1.0)
        }

        // update velocity
        vx += 0.2 * (Math.random() - 0.5) - 0.01 * vx
        vy += 0.2 * (Math.random() - 0.5) - 0.01 * vy
    }

    /**
     * Update the size of the particles the scale is change between [minRadius / maxRadius, 1], since
     * the scale is multiplied by the maxRadius to get current particle size, and the minRadius is the
     * minimum allowed size.
     */
    fun updateSize() {

        // change opacity minRadius/maxRadius -> 1 -> minRadius/maxRadius -> 1 ...
        if (isSizeIncreased) {
            scale += scaleFact

            if (scale >= 1.0) {
                scale = 1.0
                isSizeIncreased = false
                scaleFact = Math.random() / 50.0
            }
        } else {
            scale -= scaleFact
            if (scale * maxRadius <= minRadius) {
                scale = minRadius / maxRadius
                isSizeIncreased = true
                scaleFact = Math.random() / 50.0
            }
        }
    }

    /**
     * Update the opacity of the particles the opacity is changed between [0, 1]
     * and it is constantly changing from one end to the other 0->1->0->1->...
     */
    fun updateOpacity() {

        // change opacity 0 -> 1 -> 0...
        if (isOpacityIncreased) {
            opacity += opacityFact

            if (opacity >= 1.0) {
                opacity = 1.0
                isOpacityIncreased = false
                opacityFact = Math.random() / 50.0
            }
        } else {
            opacity -= opacityFact
            if (opacity <= 0) {
                opacity = 0.0
                isOpacityIncreased = true
                opacityFact = Math.random() / 50.0
            }
        }
    }

    /**
     * Draw particle on given canvas
     * @param canvas canvas where the particle should be drawn
     * @param paint paint object holding info about the drawing
     */
    override fun draw(canvas: Canvas, paint: Paint) {

        // set color before alpha, otherwise alpha will not be applied
        paint.color = color
        paint.alpha = (opacity * 255).toInt()

        // translate to particle position and scale to match particles size this
        // is done to keep the the RadialGradient normal, another way is to use
        // cached bitmap with the maximum size, and use it to draw the particles
        canvas.save()
        canvas.translate(x.toFloat(), y.toFloat())
        canvas.scale(scale.toFloat(), scale.toFloat())

        // draw circle
        canvas.drawCircle(0f, 0f, maxRadius.toFloat(), paint)

        canvas.restore()
    }

}
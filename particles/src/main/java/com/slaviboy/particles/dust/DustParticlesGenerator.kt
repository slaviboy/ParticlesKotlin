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

import android.graphics.*
import com.slaviboy.particles.main.ParticleGenerator

/**
 * Simple class for creating dust particles, with fully customizable properties.
 * @param viewWidth view with
 * @param viewHeight view height
 * @param minRadius minimum allowed particles radius
 * @param maxRadius maximum allowed particles radius
 * @param numberParticles total number of particles
 * @param particlesColor particles solid color
 * @param particlesRadialGradient if particles have radial gradient
 */
class DustParticlesGenerator(
    viewWidth: Int = 0,
    viewHeight: Int = 0,
    numberParticles: Int = 100,
    particlesColor: Int = Color.BLACK,
    particlesSpeed: Double = 5.0,
    isVisible: Boolean = true,
    minRadius: Double = 1.0,
    maxRadius: Double = 10.0,
    particlesRadialGradient: RadialGradient? = null
) : ParticleGenerator(viewWidth, viewHeight, numberParticles, particlesColor, particlesSpeed, isVisible) {

    var particlesRadialGradient: RadialGradient? = particlesRadialGradient
        set(value) {
            paint.shader = value
            field = value
        }


    var minRadius: Double = minRadius
        set(value) {
            particles.forEach {
                it.minRadius = value
            }
            field = value
        }
    var maxRadius: Double = maxRadius
        set(value) {
            particles.forEach {
                it.maxRadius = value
            }
            field = value
        }

    init {
        if (particlesRadialGradient != null) {
            paint.shader = particlesRadialGradient
        }
    }

    internal var particles: Array<DustParticle> = Array(numberParticles) {

        // create particle with random position inside the view
        DustParticle(
            x = (Math.random() * viewWidth),
            y = (Math.random() * viewHeight),
            vx = 0.0,
            vy = 0.0,
            scaleFact = (minRadius / maxRadius),
            speed = particlesSpeed,
            minRadius = minRadius,
            maxRadius = maxRadius
        )
    }

    /**
     * Method for updating the position, opacity and size of all particles
     */
    override fun update() {
        particles.forEach {
            it.update(viewWidth.toDouble(), viewHeight.toDouble())
            it.updateOpacity()
            it.updateSize()
        }
    }

    /**
     * Method that draw all the particles on given canvas.
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

            paint.alpha = 22
            paint.style = Paint.Style.FILL
            particles.forEach {
                it.draw(drawCanvas, paint)
            }
            paint.alpha = 255
        }

        // draw everything on the bitmap if clearing is disabled
        if (!clearCanvas) {
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
        }
    }
}
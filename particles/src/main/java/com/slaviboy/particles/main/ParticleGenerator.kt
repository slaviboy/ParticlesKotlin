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
package com.slaviboy.particles.main

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.*

abstract class ParticleGenerator(
    viewWidth: Int = 0,
    viewHeight: Int = 0,
    var numberParticles: Int = 50,
    var particlesColor: Int = Color.BLACK,
    var particlesSpeed: Double = 5.0,
    isVisible: Boolean = true
) {

    internal lateinit var bitmap: Bitmap
    internal lateinit var bitmapCanvas: Canvas
    internal var updateBitmap: Boolean = true

    var viewWidth: Int = viewWidth
        set(value) {
            updateBitmap = true
            field = value
        }
    var viewHeight: Int = viewHeight
        set(value) {
            updateBitmap = true
            field = value
        }

    var isVisible: Boolean = isVisible
        set(value) {
            clearBitmap()
            field = value
        }

    internal val paint: Paint

    init {

        // initialize the paint object
        paint = Paint().apply {
            style = Paint.Style.FILL
            color = particlesColor
            isAntiAlias = true
        }
    }

    /**
     * Method that updates all particles
     */
    abstract fun update()

    /**
     * Method that draws all particles on given canvas
     * @param canvas canvas where the particles will be drawn
     * @param paint paint object with properties for the drawing
     * @param clearCanvas if value is set to false, the drawing is done on the bitmap, without clearing the canvas
     */
    abstract fun draw(canvas: Canvas = bitmapCanvas, paint: Paint = this.paint, clearCanvas: Boolean = false)

    /**
     * Clear the bitmap
     */
    fun clearBitmap() {
        if (::bitmap.isInitialized) {
            bitmap.eraseColor(Color.TRANSPARENT)
        }
    }

    companion object {

        /**
         * Generate random integer representation of a color
         */
        fun randomColor(hasAlpha: Boolean = false): Int {
            return Color.argb(
                if (hasAlpha) (Math.random() * 256).toInt() else 255,
                (Math.random() * 256).toInt(),
                (Math.random() * 256).toInt(),
                (Math.random() * 256).toInt()
            )
        }

        /**
         * Generate random number in integer range
         * @example: [12, 65]
         */
        fun randomDouble(min: Int, max: Int): Double {
            return (Random().nextInt(max - min + 1) + min).toDouble()
        }

        /**
         * Generate random number in double range
         * @example: [0.3, 0.9]
         */
        fun randomDouble(min: Double, max: Double): Double {
            return (min + (max - min) * Random().nextDouble())
        }
    }
}
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

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Simple abstract class for the line and dust particles
 * @param x center x coordinate of the particle
 * @param y center y coordinate of the particle
 * @param vx velocity in the horizontal direction
 * @param vy velocity in the vertical direction
 * @param speed speed of the particle
 */
abstract class Particle(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var vx: Double = 0.0,
    var vy: Double = 0.0,
    var speed: Double = 1.0
) {

    /**
     * Update the position of the particle by moving in to a random direction
     * @param viewWidth with of the view
     * @param viewHeight height of the view
     */
    abstract fun update(viewWidth: Double, viewHeight: Double)

    /**
     * Draw particle on given canvas
     * @param canvas canvas where the particle should be drawn
     * @param paint paint object holding info about the drawing
     */
    abstract fun draw(canvas: Canvas, paint: Paint)

}
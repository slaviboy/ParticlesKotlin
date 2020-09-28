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
package com.slaviboy.simpleparticlesexample

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log

/**
 * Simple image button class with, the ability to use gray background image, from the
 * original drawable with the custom button enable property. The gray drawable is cached
 * on the first conversion from the original.
 */
class ImageButton : androidx.appcompat.widget.AppCompatImageButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var transitionDuration: Int = 150                    // image transition in ms(milliseconds)
    var transitionDrawable: TransitionDrawable? = null  // image transition object
    var originalBackground: Drawable? = null            // original drawable background image
    var grayBackground: Drawable? =
        null                // gray drawable image generated from the original
    var originalOpacity: Float = 1.0f                   // view opacity, when enabled
    var grayOpacity: Float = 0.8f                       // view opacity, when disabled
    var isButtonEnabled: Boolean = true                 // whether the button is enabled
        set(value) {
            field = value
            changeImage(value)
        }

    /**
     * Switch the button enabled property
     */
    fun switchButtonEnabled() {
        isButtonEnabled = !isButtonEnabled
    }

    /**
     * Change image drawable to gray or the original depending if the image button
     * is enabled, with the property -isButtonEnabled
     * @param isGray whether the fray drawable should be used
     */
    private fun changeImage(isGray: Boolean) {
        if (isGray) {
            alpha = originalOpacity
        } else {
            originalOpacity = alpha
            alpha = grayOpacity
            if (grayBackground != null) {
                grayBackground
            } else {

                // cache the gray drawable to the variable -grayBackground
                originalBackground = drawable.getConstantState()?.newDrawable()?.mutate()
                val drawableTemp = drawable.mutate()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    drawableTemp.colorFilter = BlendModeColorFilter(Color.GRAY, BlendMode.SRC_IN)
                } else {
                    drawableTemp.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
                }
                grayBackground = drawableTemp

                val layers = arrayOf(originalBackground, grayBackground)
                transitionDrawable = TransitionDrawable(layers)
                setImageDrawable(transitionDrawable)
            }
        }

        if (!isGray) {
            transitionDrawable?.startTransition(transitionDuration)
        } else {
            transitionDrawable?.reverseTransition(transitionDuration)
        }
    }

}
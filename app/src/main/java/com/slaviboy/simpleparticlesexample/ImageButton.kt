package com.slaviboy.simpleparticlesexample

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet

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
 * Simple image button class with, the ability to use gray background image, from the
 * original drawable with the custom button enable property. The gray drawable is cached
 * on the first conversion from the original.
 */
class ImageButton : androidx.appcompat.widget.AppCompatImageButton {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var originalBackground: Drawable? = null      // original drawable background image
    var grayBackground: Drawable? = null          // gray drawable image generated from the original
    var originalOpacity: Float = 1.0f             // view opacity, when enabled
    var grayOpacity: Float = 0.8f                 // view opacity, when disabled
    var isButtonEnabled: Boolean = true           // whether the button is enabled
        set(value) {
            field = value
            changeImage(value)
        }
        get() {
            return field
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
     *
     * @param isGray whether the fray drawable should be used
     */
    private fun changeImage(isGray: Boolean) {
        val icon = if (isGray) {
            alpha = originalOpacity
            originalBackground
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
                drawableTemp
            }
        }
        setImageDrawable(icon)
    }

}
package com.slaviboy.simpleparticlesexample.drawing

import android.graphics.Canvas

interface ISurfaceHolder {
    fun unlockCanvasAndPost(canvas: Canvas?)
    fun lockCanvas(): Canvas?
}
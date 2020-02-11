package com.slaviboy.simpleparticlesexample.drawing

import android.graphics.Canvas
import android.view.SurfaceHolder

class SurfaceViewHolder(val surfaceHolder: SurfaceHolder) :
    ISurfaceHolder {

    override fun unlockCanvasAndPost(canvas: Canvas?) {
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun lockCanvas(): Canvas? {
        return surfaceHolder.lockCanvas()
    }
}
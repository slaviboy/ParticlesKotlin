package com.slaviboy.simpleparticlesexample.drawing

import android.graphics.Canvas
import com.slaviboy.simpleparticlesexample.drawing.ISurfaceHolder
import com.slaviboy.simpleparticlesexample.drawing.TextureView

class TextureViewHolder(private val textureView: TextureView) :
    ISurfaceHolder {

    override fun unlockCanvasAndPost(canvas: Canvas?) {
        textureView.unlockCanvasAndPost(canvas)
    }

    override fun lockCanvas(): Canvas? {
        return textureView.lockCanvas()
    }

}
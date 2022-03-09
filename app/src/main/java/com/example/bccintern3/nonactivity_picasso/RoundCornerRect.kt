package com.example.bccintern2.picasso

import android.graphics.*
import android.os.Build
import com.squareup.picasso.Transformation

class RoundCornerRect(
    private val radius:Float,
    private val paddingLeft:Float,
    private val paddingRight: Float,
    private val paddingTop: Float,
    private val paddingBottom: Float
    ): Transformation {

    override fun transform(source: Bitmap?): Bitmap {
        val bitmap = Bitmap.createBitmap(source!!.width, source.height, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        val shader = BitmapShader(source!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        val rect = RectF(0.0f-paddingLeft, 0.0f-paddingTop, source.width.toFloat()-paddingRight, source.height.toFloat()-paddingBottom)
        canvas.drawRoundRect(rect, radius, radius, paint)
        source.recycle()

        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}
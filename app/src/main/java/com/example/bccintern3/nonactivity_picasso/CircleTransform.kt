package com.example.bccintern2.picasso

import android.graphics.*
import com.squareup.picasso.Transformation

class CircleTransform: Transformation {
    override fun transform(source: Bitmap?): Bitmap {
        var size = Math.min(source!!.width,source!!.height)

        var x = (source!!.width-size)/2
        var y = (source!!.height-size)/2

        var squareBitmap = Bitmap.createBitmap(source!!,x,y,size,size)
        if(squareBitmap!= source){
            source.recycle()
        }

        var bitmap = Bitmap.createBitmap(size,size, source!!.getConfig())
        var canvas = Canvas(bitmap)
        var paint = Paint()

        var shader = BitmapShader(squareBitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)

        paint.setShader(shader)
        paint.setAntiAlias(true)

        var r:Float = size/2f

        canvas.drawCircle(r,r,r,paint)

        squareBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}
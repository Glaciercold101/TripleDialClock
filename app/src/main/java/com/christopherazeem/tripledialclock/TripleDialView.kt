package com.christopherazeem.tripledialclock
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
class TripleDialView @JvmOverloads constructor(c: Context, a: AttributeSet?=null): View(c,a) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
        paint.color = Color.WHITE
        paint.textSize = 80f
        paint.textAlign = Paint.Align.CENTER
        val now = Calendar.getInstance()
        val t = String.format("%02d:%02d:%02d", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND))
        canvas.drawText(t, width/2f, height/2f, paint)
        postInvalidateDelayed(1000)
    }
}

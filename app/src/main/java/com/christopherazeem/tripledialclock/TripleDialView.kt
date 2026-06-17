package com.christopherazeem.tripledialclock

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.*

class TripleDialView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.BLACK }
    private val dialPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
    }
    private val hourHand = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.CYAN; strokeWidth = 8f; strokeCap = Paint.Cap.ROUND }
    private val minHand = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.MAGENTA; strokeWidth = 6f; strokeCap = Paint.Cap.ROUND }
    private val secHand = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.YELLOW; strokeWidth = 4f; strokeCap = Paint.Cap.ROUND }

    private val cal = Calendar.getInstance()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val cx = w/2f
        val cy = h/2f
        val radius = min(w,h) * 0.45f

        canvas.drawRect(0f,0f,w,h, bgPaint)

        // three dials
        canvas.drawCircle(cx,cy,radius, dialPaint)
        canvas.drawCircle(cx,cy,radius*0.67f, dialPaint)
        canvas.drawCircle(cx,cy,radius*0.34f, dialPaint)

        // numbers
        textPaint.textSize = radius*0.09f
        for (i in 1..12) {
            val ang = Math.toRadians((i*30 - 90).toDouble())
            val x = cx + cos(ang).toFloat() * radius*0.85f
            val y = cy + sin(ang).toFloat() * radius*0.85f + textPaint.textSize/3
            canvas.drawText(i.toString(), x, y, textPaint)
        }
        textPaint.textSize = radius*0.07f
        for (i in 0 until 60 step 5) {
            val ang = Math.toRadians((i*6 - 90).toDouble())
            val x = cx + cos(ang).toFloat() * radius*0.57f
            val y = cy + sin(ang).toFloat() * radius*0.57f + textPaint.textSize/3
            canvas.drawText(i.toString(), x, y, textPaint)
        }

        cal.timeInMillis = System.currentTimeMillis()
        val hour = cal.get(Calendar.HOUR)
        val minute = cal.get(Calendar.MINUTE)
        val second = cal.get(Calendar.SECOND)
        val milli = cal.get(Calendar.MILLISECOND)

        val secAngle = Math.toRadians((second + milli/1000.0)*6 - 90)
        val minAngle = Math.toRadians((minute + second/60.0)*6 - 90)
        val hourAngle = Math.toRadians((hour + minute/60.0)*30 - 90)

        // hands
        canvas.drawLine(cx, cy, cx + cos(hourAngle).toFloat()*radius*0.5f, cy + sin(hourAngle).toFloat()*radius*0.5f, hourHand)
        canvas.drawLine(cx, cy, cx + cos(minAngle).toFloat()*radius*0.62f, cy + sin(minAngle).toFloat()*radius*0.62f, minHand)
        canvas.drawLine(cx, cy, cx + cos(secAngle).toFloat()*radius*0.8f, cy + sin(secAngle).toFloat()*radius*0.8f, secHand)

        canvas.drawCircle(cx,cy,8f, bgPaint.apply { color = Color.WHITE; style = Paint.Style.FILL })

        postInvalidateOnAnimation()
    }
}

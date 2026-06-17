package com.christopherazeem.tripledialclock

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.*

class TripleDialView @JvmOverloads constructor(c: Context, a: AttributeSet? = null) : View(c, a) {

    private val bg = Paint().apply { color = Color.BLACK }
    private val dial = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; style = Paint.Style.STROKE; strokeWidth = 3f; alpha = 180 }
    private val num = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; textAlign = Paint.Align.CENTER }
    private val hourP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.CYAN; strokeWidth = 10f; strokeCap = Paint.Cap.ROUND }
    private val minP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.MAGENTA; strokeWidth = 7f; strokeCap = Paint.Cap.ROUND }
    private val secP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.YELLOW; strokeWidth = 5f; strokeCap = Paint.Cap.ROUND }
    private val center = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }

    private val cal = Calendar.getInstance()

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()
        val cx = w/2f
        val cy = h/2f
        val r = min(w,h)*0.43f

        canvas.drawRect(0f,0f,w,h,bg)

        // dials
        canvas.drawCircle(cx,cy,r, dial)
        canvas.drawCircle(cx,cy,r*0.66f, dial)
        canvas.drawCircle(cx,cy,r*0.33f, dial)

        // hour numbers 1-12 outer
        num.textSize = r*0.11f
        for (i in 1..12) {
            val a = Math.toRadians(i*30.0 - 90)
            canvas.drawText(i.toString(), cx + cos(a).toFloat()*r*0.82f, cy + sin(a).toFloat()*r*0.82f + num.textSize/3, num)
        }
        // minute numbers
        num.textSize = r*0.08f
        for (i in 0 until 60 step 5) {
            val a = Math.toRadians(i*6.0 - 90)
            canvas.drawText(i.toString(), cx + cos(a).toFloat()*r*0.55f, cy + sin(a).toFloat()*r*0.55f + num.textSize/3, num)
        }

        cal.timeInMillis = System.currentTimeMillis()
        val h0 = cal.get(Calendar.HOUR)
        val m0 = cal.get(Calendar.MINUTE)
        val s0 = cal.get(Calendar.SECOND)
        val ms = cal.get(Calendar.MILLISECOND)

        val sa = Math.toRadians((s0 + ms/1000.0)*6 - 90)
        val ma = Math.toRadians((m0 + s0/60.0)*6 - 90)
        val ha = Math.toRadians((h0 + m0/60.0)*30 - 90)

        canvas.drawLine(cx,cy, cx+cos(ha).toFloat()*r*0.5f, cy+sin(ha).toFloat()*r*0.5f, hourP)
        canvas.drawLine(cx,cy, cx+cos(ma).toFloat()*r*0.62f, cy+sin(ma).toFloat()*r*0.62f, minP)
        canvas.drawLine(cx,cy, cx+cos(sa).toFloat()*r*0.78f, cy+sin(sa).toFloat()*r*0.78f, secP)

        canvas.drawCircle(cx,cy,10f, center)

        postInvalidateOnAnimation()
    }
}

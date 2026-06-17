package com.christopherazeem.tripledialclock

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.*

class TripleDialView @JvmOverloads constructor(c: Context, a: AttributeSet? = null) : View(c, a) {
    private val bg = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.BLACK }
    private val ring = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.LTGRAY; style = Paint.Style.STROKE; strokeWidth = 3f }
    private val txt = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; textAlign = Paint.Align.CENTER }
    private val hourP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.CYAN; strokeWidth = 10f; strokeCap = Paint.Cap.ROUND }
    private val minP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.MAGENTA; strokeWidth = 8f; strokeCap = Paint.Cap.ROUND }
    private val secP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.YELLOW; strokeWidth = 6f; strokeCap = Paint.Cap.ROUND }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat(); val h = height.toFloat()
        val cx = w/2f; val cy = h/2f; val r = min(w,h)*0.43f
        canvas.drawRect(0f,0f,w,h,bg)

        val rOut = r
        val rMid = r*0.66f
        val rIn = r*0.33f

        // rings
        canvas.drawCircle(cx,cy,rOut,ring)
        canvas.drawCircle(cx,cy,rMid,ring)
        canvas.drawCircle(cx,cy,rIn,ring)

        // outer hours 1-12
        txt.textSize = rOut*0.09f
        for(i in 1..12){
            val a = Math.toRadians(i*30.0-90); val x = cx+cos(a).toFloat()*rOut*0.85f; val y = cy+sin(a).toFloat()*rOut*0.85f+txt.textSize/3
            canvas.drawText(i.toString(),x,y,txt)
        }
        // middle minutes
        txt.textSize = rMid*0.075f
        for(i in 0 until 60 step 5){
            val a = Math.toRadians(i*6.0-90); val x = cx+cos(a).toFloat()*rMid*0.82f; val y = cy+sin(a).toFloat()*rMid*0.82f+txt.textSize/3
            canvas.drawText(i.toString(),x,y,txt)
        }
        // inner seconds
        txt.textSize = rIn*0.12f
        for(i in 0 until 60 step 5){
            val a = Math.toRadians(i*6.0-90); val x = cx+cos(a).toFloat()*rIn*0.7f; val y = cy+sin(a).toFloat()*rIn*0.7f+txt.textSize/3
            canvas.drawText(i.toString(),x,y,txt)
        }

        val cal = Calendar.getInstance()
        val hVal = cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE)/60f
        val mVal = cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND)/60f
        val sVal = cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND)/1000f

        val ha = Math.toRadians(hVal*30.0-90); val ma = Math.toRadians(mVal*6.0-90); val sa = Math.toRadians(sVal*6.0-90)

        // hands confined to their own dial
        canvas.drawLine(cx,cy, cx+cos(ha).toFloat()*rOut*0.7f, cy+sin(ha).toFloat()*rOut*0.7f, hourP)
        canvas.drawLine(cx,cy, cx+cos(ma).toFloat()*rMid*0.7f, cy+sin(ma).toFloat()*rMid*0.7f, minP)
        canvas.drawLine(cx,cy, cx+cos(sa).toFloat()*rIn*0.7f,  cy+sin(sa).toFloat()*rIn*0.7f,  secP)

        canvas.drawCircle(cx,cy,10f, Paint().apply{color=Color.WHITE})

        postInvalidateOnAnimation()
    }
}

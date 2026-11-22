package com.dripxiecon.opendyslexic.keyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import com.dripxiecon.opendyslexic.layouts.KeyDefinition

/** Lightweight custom-drawn keyboard view; no per-key view hierarchy. */
class KeyboardView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF()
    private var keys: List<KeyDefinition> = emptyList()
    var onKeyPressed: ((KeyDefinition) -> Unit)? = null

    private val keyTypeface: Typeface = runCatching {
        Typeface.createFromAsset(context.assets, "fonts/OpenDyslexic-Regular.otf")
    }.getOrDefault(Typeface.DEFAULT)

    fun setKeys(value: List<KeyDefinition>) {
        keys = value
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = (240 * resources.displayMetrics.density).toInt()
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (keys.isEmpty()) return

        val columns = 10
        val rows = (keys.size + columns - 1) / columns
        val gap = 5f * resources.displayMetrics.density
        val cellWidth = (width - gap * (columns + 1)) / columns
        val cellHeight = (height - gap * (rows + 1)) / rows

        keys.forEachIndexed { index, key ->
            val row = index / columns
            val column = index % columns
            bounds.set(
                gap + column * (cellWidth + gap),
                gap + row * (cellHeight + gap),
                gap + column * (cellWidth + gap) + cellWidth,
                gap + row * (cellHeight + gap) + cellHeight
            )
            paint.color = 0xFFE3E3E8.toInt()
            canvas.drawRoundRect(bounds, 10f, 10f, paint)
            paint.color = 0xFF202027.toInt()
            paint.typeface = keyTypeface
            paint.textSize = 22f * resources.displayMetrics.scaledDensity
            paint.textAlign = Paint.Align.CENTER
            val baseline = bounds.centerY() - (paint.ascent() + paint.descent()) / 2
            canvas.drawText(key.label, bounds.centerX(), baseline, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && keys.isNotEmpty()) {
            val columns = 10
            val gap = 5f * resources.displayMetrics.density
            val cellWidth = (width - gap * (columns + 1)) / columns
            val rows = (keys.size + columns - 1) / columns
            val cellHeight = (height - gap * (rows + 1)) / rows
            val column = ((event.x - gap) / (cellWidth + gap)).toInt()
            val row = ((event.y - gap) / (cellHeight + gap)).toInt()
            val index = row * columns + column
            if (column in 0 until columns && row >= 0 && index in keys.indices) {
                onKeyPressed?.invoke(keys[index])
            }
            performClick()
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}

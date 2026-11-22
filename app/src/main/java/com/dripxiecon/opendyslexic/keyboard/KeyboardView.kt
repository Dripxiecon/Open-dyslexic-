package com.dripxiecon.opendyslexic.keyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import com.dripxiecon.opendyslexic.layouts.KeyDefinition
import com.dripxiecon.opendyslexic.layouts.KeyKind
import com.dripxiecon.opendyslexic.layouts.KeyRow

/** Single custom-drawn view: avoids allocating a separate Android View for every key. */
class KeyboardView(context: Context) : View(context) {
    private data class HitTarget(val bounds: RectF, val key: KeyDefinition)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val hitTargets = ArrayList<HitTarget>()
    private var rows: List<KeyRow> = emptyList()
    var onKeyPressed: ((KeyDefinition) -> Unit)? = null

    private val density = resources.displayMetrics.density
    private val keyTypeface: Typeface = runCatching {
        Typeface.createFromAsset(context.assets, "fonts/OpenDyslexic-Regular.otf")
    }.getOrDefault(Typeface.DEFAULT)

    fun setRows(value: List<KeyRow>) {
        rows = value
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = (264 * density).toInt()
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hitTargets.clear()
        if (rows.isEmpty()) return

        val gap = 5f * density
        val verticalGap = 7f * density
        val rowHeight = (height - verticalGap * (rows.size + 1)) / rows.size
        val normalKeyWidth = (width - gap * 11f) / 10f

        rows.forEachIndexed { rowIndex, row ->
            val totalWeight = row.keys.sumOf { it.weight.toDouble() }.toFloat()
            val scale = if (totalWeight > 0f) {
                ((width - gap * (row.keys.size + 1) - row.startInset * normalKeyWidth) / totalWeight)
                    .coerceAtLeast(0f)
            } else normalKeyWidth
            var left = gap + row.startInset * normalKeyWidth
            val top = verticalGap + rowIndex * (rowHeight + verticalGap)

            row.keys.forEach { key ->
                val keyWidth = key.weight * scale
                val rect = RectF(left, top, left + keyWidth, top + rowHeight)
                drawKey(canvas, rect, key)
                hitTargets.add(HitTarget(RectF(rect), key))
                left += keyWidth + gap
            }
        }
    }

    private fun drawKey(canvas: Canvas, rect: RectF, key: KeyDefinition) {
        paint.color = when (key.kind) {
            KeyKind.ENTER -> 0xFF6477B8.toInt()
            KeyKind.SHIFT, KeyKind.DELETE, KeyKind.SWITCH_NUMBERS,
            KeyKind.SWITCH_SYMBOLS, KeyKind.SWITCH_LETTERS -> 0xFFB9BAC5.toInt()
            else -> 0xFFF1F1F5.toInt()
        }
        canvas.drawRoundRect(rect, 9f * density, 9f * density, paint)

        paint.color = if (key.kind == KeyKind.ENTER) 0xFFFFFFFF.toInt() else 0xFF202027.toInt()
        paint.typeface = keyTypeface
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = (if (key.label.length > 4) 13f else 20f) *
            resources.displayMetrics.scaledDensity
        val baseline = rect.centerY() - (paint.ascent() + paint.descent()) / 2f
        canvas.drawText(key.label, rect.centerX(), baseline, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                hitTargets.firstOrNull { it.bounds.contains(event.x, event.y) }
                    ?.let { onKeyPressed?.invoke(it.key) }
                performClick()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}

package com.poran.customcircularprogressbar.component

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import com.poran.customcircularprogressbar.R

class CircularProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) {
    private val defaultBackgroundColor = Color.GRAY
    private val defaultProgressColor = Color.GREEN
    private var strokedWidth: Float = 0f
    private var progressPaint: Paint
    private var backgroundProgressPaint: Paint
    private var textProgressPaint: Paint
    var progressValue: Int
    companion object {
        const val DEFAULT_STROKE_WIDTH = 5f
    }
    init {
        val  typedArray = context.obtainStyledAttributes(attrs, R.styleable.Progress)
        val backgroundColor = typedArray.getColor(R.styleable.Progress_colorBackground, defaultBackgroundColor)
        val progressColor = typedArray.getColor(R.styleable.Progress_colorProgress, defaultProgressColor)
        val defaultProgress = typedArray.getInteger(R.styleable.Progress_defaultProgressValue, 0)
        strokedWidth = typedArray.getDimension(R.styleable.Progress_strokeWidth, DEFAULT_STROKE_WIDTH)

        // paint tools for paint circular progress
        progressPaint = Paint().apply {
            isAntiAlias = true
            color = progressColor
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = strokedWidth
        }

        backgroundProgressPaint = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = strokedWidth
        }

        textProgressPaint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            strokeWidth = 0f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create("cabin", Typeface.BOLD)
            textSize = 100f
        }
        progressValue = defaultProgress
        typedArray.recycle()
    }

}
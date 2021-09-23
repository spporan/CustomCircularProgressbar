package com.poran.customcircularprogressbar.component

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.poran.customcircularprogressbar.R
import kotlin.math.min

class CircularProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    private val defaultBackgroundColor = Color.GRAY
    private val defaultProgressColor = Color.GREEN
    private val dpToPx = Resources.getSystem().displayMetrics.density
    private val defaultStrokeWidth = 16 * dpToPx
    private var strokedWidth: Float = defaultStrokeWidth
    private val progressPaint: Paint
    private val backgroundProgressPaint: Paint
    private val textProgressPaint: Paint
    private lateinit var progressBounds: RectF
    private val animationInterpolator by lazy { DecelerateInterpolator() }

    private var sweepAngle: Float = 250f
    private var centerX : Float = 0f
    private var centerY : Float = 0f
    var progressValue: Int = 0
        set(value) {
            field = value
            ValueAnimator.ofFloat(sweepAngle, 360f / 100f * progressValue).apply {
                interpolator = animationInterpolator
                duration = 300
                addUpdateListener { animation ->
                    sweepAngle = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }

    init {
        val  typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgress)
        val backgroundColor = typedArray.getColor(R.styleable.CircularProgress_colorBackground, defaultBackgroundColor)
        val progressColor = typedArray.getColor(R.styleable.CircularProgress_colorProgress, defaultProgressColor)
        val defaultProgress = typedArray.getInteger(R.styleable.CircularProgress_defaultProgressValue, 0)
        strokedWidth = typedArray.getDimension(R.styleable.CircularProgress_strokeWidth, defaultStrokeWidth)

        // paint tools for paint circular progress
        progressPaint = Paint().apply {
            isAntiAlias = true
            color = progressColor
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = strokedWidth
        }

        // paint background circle
        backgroundProgressPaint = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = strokedWidth
        }
        // paint progress text value
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

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        val radius = (min(width, height)/2.0 * 0.7).toFloat()
        val viewWidth = layoutParams.width.toFloat()
        val viewHeight = layoutParams.height.toFloat()

        val arcStart = strokedWidth + 50
        val arcEnd = viewWidth - arcStart
        progressBounds = RectF(arcStart, arcStart, arcEnd, arcEnd)

        centerX = viewWidth/ 2f
        centerY = viewHeight / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(progressBounds, 90f, 360f, false, backgroundProgressPaint)

        canvas?.drawArc(progressBounds, 90f, sweepAngle, false, progressPaint)

        canvas?.drawText("$progressValue%", centerX, centerY + 35f, textProgressPaint)
    }


}
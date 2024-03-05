package kz.onelab.lesson2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlin.math.min

private const val MAX_COUNT = 9999
private const val MAX_COUNT_STRING = "9999"

class TallyCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val numberPaint: Paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundRect: RectF
    private val cornerRadius: Float
    private var count: Int = 0
    private var displayedCount = ""
    private var lastTouchX: Float = 0f
    private var lastTouchY: Float = 0f
    private var customTextColor: Int = Color.BLACK

    private var gestureDetector: GestureDetector

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TallyCounterView, defStyleAttr, 0)
        count = typedArray.getInteger(R.styleable.TallyCounterView_count, 0)
        setCount(count)
        customTextColor = typedArray.getColor(R.styleable.TallyCounterView_textColor, Color.BLACK)
        typedArray.recycle()

        // Set up points for canvas drawing.
        backgroundPaint.color = ContextCompat.getColor(context, R.color.purple)
        linePaint.color = ContextCompat.getColor(context, R.color.black)
        linePaint.strokeWidth = 1f
        numberPaint.color = customTextColor
        // Set the number text size to be 64sp.
        // Translate 64sp
        numberPaint.textSize = 64.sp.toFloat()

        // Allocate objects needed for canvas drawing here.
        backgroundRect = RectF()

        // Initialize drawing measurements.
        cornerRadius = (2f * resources.displayMetrics.density)
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                decrement()
                return super.onDoubleTap(e)
            }

        })
    }

    override fun onDraw(canvas: Canvas) {
        // Grab canvas dimensions.
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        // Calculate horizontal center.
        val centerX = canvasWidth * 0.5f

        // Draw the background.
        backgroundRect[0f, 0f, canvasWidth.toFloat()] = canvasHeight.toFloat()
        canvas.drawRoundRect(
            backgroundRect,
            cornerRadius,
            cornerRadius,
            backgroundPaint
        )

        // Draw baseline.
        val baselineY = Math.round(canvasHeight * 0.6f).toFloat()
        canvas.drawLine(0f, baselineY, canvasWidth.toFloat(), baselineY, linePaint)

        // Draw text.

        // Measure the width of text to display.
        val textWidth = numberPaint.measureText(displayedCount)
        // Figure out an x-coordinate that will center the text in the canvas.
        val textX = Math.round(centerX - textWidth * 0.5f).toFloat()
        // Draw.
        canvas.drawText(displayedCount, textX, baselineY, numberPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fontMetrics = numberPaint.fontMetrics


        // Measure maximum possible width of text.
        val maxTextWidth = numberPaint.measureText(MAX_COUNT_STRING)
        // Estimate maximum possible height of text.
        val maxTextHeight = -fontMetrics.top + fontMetrics.bottom


        // Add padding to maximum width calculation.
        val desiredWidth = Math.round(maxTextWidth + paddingLeft + paddingRight)


        // Add padding to maximum height calculation.
        val desiredHeight = Math.round(maxTextHeight * 2f + paddingTop + paddingBottom)


        // Reconcile size that this view wants to be with the size the parent will let it be.
        val measuredWidth: Int = reconcileSize(desiredWidth, widthMeasureSpec)
        val measuredHeight: Int = reconcileSize(desiredHeight, heightMeasureSpec)


        // Store the final measured dimensions.
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        // Get the index of the pointer associated with the action.
        val pointerIndex: Int = event.actionIndex

        // Get the action's location.
        val x: Float = event.getX(pointerIndex)
        val y: Float = event.getY(pointerIndex)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // Remember where we started.
                lastTouchX = x
                lastTouchY = y
                // It's important to return true here so that we get subsequent events.
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                // Calculate the distance moved.
                val dx: Float = x - lastTouchX
                val dy: Float = y - lastTouchY

                // Move the object.
                val layout = this.layoutParams as ViewGroup.MarginLayoutParams
                layout.leftMargin += dx.toInt()
                layout.topMargin += dy.toInt()
                this.layoutParams = layout

                // Remember this touch position for the next move event.
                lastTouchX = x
                lastTouchY = y

                // Invalidate to request a redraw.
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Release touch pressure.
            }
        }
        return super.onTouchEvent(event)
    }

    fun setCount(count: Int) {
        val min = min(count, MAX_COUNT)
        this.count = min
        displayedCount = String.format("%04d", min)
        invalidate()
    }

    fun increment() {
        setCount(count + 1)
    }

    private fun decrement() {
        setCount(count - 1)
    }

    fun reset() {
        setCount(0)
    }

    /**
     * Reconcile a desired size for the view contents with a [android.view.View.MeasureSpec]
     * constraint passed by the parent.
     *
     * This is a simplified version of [View.resolveSize]
     *
     * @param contentSize Size of the view's contents.
     * @param measureSpec A [android.view.View.MeasureSpec] passed by the parent.
     * @return A size that best fits `contentSize` while respecting the parent's constraints.
     */
    private fun reconcileSize(contentSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> if (contentSize < specSize) {
                contentSize
            } else {
                specSize
            }

            MeasureSpec.UNSPECIFIED -> contentSize
            else -> contentSize
        }
    }
}
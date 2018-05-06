package com.gildaswise.horizontalcounter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

/**
 * Created by gildaswise on 15/01/18.
 */

open class HorizontalCounter(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    companion object {

        const val TAG = "HorizontalCounter"
        const val DEFAULT_STEP_COUNT_SIZE = 1
        const val DEFAULT_TEXT_SIZE = 16
        const val DEFAULT_STEP_COUNT: Double = 1.0
        const val DEFAULT_CURRENT_VALUE: Double = 0.0
        const val DEFAULT_MAX_VALUE: Double = 999.0

    }

    private var stepValue: Double = DEFAULT_STEP_COUNT
    private var currentValue: Double = DEFAULT_CURRENT_VALUE
    private var maxValue: Double = DEFAULT_MAX_VALUE
    private var minValue: Double = -DEFAULT_MAX_VALUE
    private var plusButtonColor: Int = 0
    private var minusButtonColor: Int = 0
    private var textColor: Int = 0
    private var textSize: Int = DEFAULT_TEXT_SIZE
    private var displayingInteger = false
    private var releaseCallback: RepeatListener.ReleaseCallback? = null
    private var value: TextView? = null
    private var plusButton: Button? = null
    private var minusButton:Button? = null

    init {

        View.inflate(context, R.layout.layout, this)
        value = findViewById(R.id.value)
        plusButton = findViewById(R.id.plus)
        minusButton = findViewById(R.id.minus)

        val density = resources.displayMetrics.density
        plusButtonColor = ContextCompat.getColor(context, R.color.colorDefault)
        minusButtonColor = ContextCompat.getColor(context, R.color.colorDefault)
        textColor = ContextCompat.getColor(context, android.R.color.primary_text_light)
        textSize = (density * textSize).toInt()

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.HorizontalCounter)

        plusButtonColor = attributes.getColor(R.styleable.HorizontalCounter_plusButtonColor, plusButtonColor)
        minusButtonColor = attributes.getColor(R.styleable.HorizontalCounter_minusButtonColor, minusButtonColor)
        textColor = attributes.getColor(R.styleable.HorizontalCounter_textColor, textColor)
        textSize = attributes.getDimension(R.styleable.HorizontalCounter_textSize, textSize.toFloat()).toInt()
        currentValue = attributes.getString(R.styleable.HorizontalCounter_initialValue)?.toDouble() ?: DEFAULT_CURRENT_VALUE
        setStepValue(attributes.getString(R.styleable.HorizontalCounter_stepValue)?.toDouble() ?: DEFAULT_STEP_COUNT)
        maxValue = attributes.getString(R.styleable.HorizontalCounter_maxValue)?.toDouble() ?: DEFAULT_MAX_VALUE
        minValue = attributes.getString(R.styleable.HorizontalCounter_minValue)?.toDouble() ?: -DEFAULT_MAX_VALUE

        displayingInteger = attributes.getBoolean(R.styleable.HorizontalCounter_displayAsInteger, false)

        if (maxValue <= minValue || minValue >= maxValue) {
            throw InvalidLimitsException()
        }

        attributes.recycle()
        setupViews()

    }

    private fun setupViews() {
        prepareViews(true)
        setupValueTextView()
        setupPlusButton()
        setupMinusButton()
        prepareViews(false)
    }

    private fun setupButtons() {
        setupPlusButton()
        setupMinusButton()
    }

    private fun setupValueTextView() {
        value?.apply {
            setTextColor(textColor)
            setTextSize(this@HorizontalCounter.textSize.toFloat())
        }
        updateCurrentValue()
    }

    private fun prepareViews(initializing: Boolean) {
        value?.let {
            it.visibility = if (initializing) View.GONE else View.VISIBLE
            it.alpha = if (initializing) 0f else 1f
        }
        plusButton?.let {
            it.visibility = if (initializing) View.GONE else View.VISIBLE
            it.alpha = if (initializing) 0f else 1f
        }
        minusButton?.let {
            it.visibility = if (initializing) View.GONE else View.VISIBLE
            it.alpha = if (initializing) 0f else 1f
        }
    }

    private fun setupPlusButton() {
        plusButton?.apply {
            setTextColor(plusButtonColor)
            setOnTouchListener(getPlusButtonListener())
        }
    }

    private fun getPlusButtonListener(): View.OnTouchListener {
        return RepeatListener(View.OnClickListener {
            getCurrentValue()?.let {
                if (it < getMaxValue() && it + getStepValue() <= getMaxValue()) {
                    val result = it + getStepValue()
                    setCurrentValue(result)
                    updateCurrentValue()
                }
            }
        }, getReleaseCallback())
    }

    private fun getStepValue(): Double {
        return stepValue
    }

    private fun updateCurrentValue() {
        if (getCurrentValue() != DEFAULT_CURRENT_VALUE) {
            value?.text = if (isDisplayingInteger())
                String.format(Locale.US, "%d", getCurrentValue()?.toInt())
            else
                String.format("%." + getStepValueCount() + "f", getCurrentValue()?.toFloat())
        }
    }

    private fun setupMinusButton() {
        minusButton?.apply {
            setTextColor(minusButtonColor)
            setOnTouchListener(getMinusButtonListener())
        }
    }

    fun setOnReleaseListener(releaseCallback: RepeatListener.ReleaseCallback) {
        this.releaseCallback = releaseCallback
    }

    fun getReleaseCallback(): RepeatListener.ReleaseCallback? {
        return releaseCallback
    }

    private fun getMinusButtonListener(): View.OnTouchListener {
        return RepeatListener(View.OnClickListener {
            getCurrentValue()?.let {
                if (it > getMinValue() && it - getStepValue() >= getMinValue()) {
                    setCurrentValue(it - getStepValue())
                    updateCurrentValue()
                }
            }
        }, getReleaseCallback())
    }

    fun setStepValue(value: Double) {
        this.stepValue = value
    }

    fun getCurrentValue(): Double? {
        return currentValue
    }

    fun setCurrentValue(value: Double) {
        this.currentValue = value
        updateCurrentValue()
    }

    fun getMaxValue(): Double {
        return maxValue
    }

    fun setMaxValue(maxValue: Double) {
        if (maxValue <= getMinValue()) {
            throw InvalidLimitsException()
        }
        this.maxValue = maxValue
    }

    fun getMinValue(): Double {
        return minValue
    }

    fun setMinValue(minValue: Double) {
        if (minValue >= getMaxValue()) {
            throw InvalidLimitsException()
        }
        this.minValue = minValue
    }

    fun getPlusButtonColor(): Int {
        return plusButtonColor
    }

    fun setPlusButtonColor(plusButtonColor: Int) {
        this.plusButtonColor = plusButtonColor
        plusButton?.setTextColor(plusButtonColor)
    }

    fun getMinusButtonColor(): Int {
        return minusButtonColor
    }

    fun setMinusButtonColor(minusButtonColor: Int) {
        this.minusButtonColor = minusButtonColor
        minusButton?.setTextColor(minusButtonColor)
    }

    fun isDisplayingInteger(): Boolean {
        return displayingInteger
    }

    fun setDisplayingInteger(displayingInteger: Boolean) {
        this.displayingInteger = displayingInteger
        updateCurrentValue()
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        value?.setTextColor(textColor)
    }

    fun getTextSize(): Int {
        return textSize
    }

    fun setTextSize(textSize: Int) {
        this.textSize = (textSize * resources.displayMetrics.density).toInt()
        value?.textSize = this.textSize.toFloat()
    }

    fun getStepValueCount(): Int {
        if (stepValue != DEFAULT_STEP_COUNT) {
            return stepValue.toString().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].length ?: DEFAULT_STEP_COUNT_SIZE
        }
        return DEFAULT_STEP_COUNT_SIZE
    }

}
package com.gildaswise.horizontalcounter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

/*
 * Copyright 2018 - Gildásio Filho (@gildaswise)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

open class HorizontalCounter(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    companion object {

        const val TAG = "HorizontalCounter"
        const val DEFAULT_STEP_COUNT_SIZE = 1
        const val DEFAULT_TEXT_SIZE = 16
        const val DEFAULT_STEP_COUNT: Double = 1.0
        const val DEFAULT_CURRENT_VALUE: Double = 0.0
        const val DEFAULT_MAX_VALUE: Double = 999.0

        @DrawableRes private var DEFAULT_PLUS_ICON: Int = R.drawable.plus
        @DrawableRes private var DEFAULT_MINUS_ICON: Int = R.drawable.minus

    }

    private var stepValue: Double = DEFAULT_STEP_COUNT
    private var currentValue: Double = DEFAULT_CURRENT_VALUE
    private var maxValue: Double = DEFAULT_MAX_VALUE
    private var minValue: Double = -DEFAULT_MAX_VALUE

    @ColorInt private var plusButtonColor: Int = 0
    @ColorInt private var minusButtonColor: Int = 0
    private var plusIcon: Drawable? = ContextCompat.getDrawable(context, DEFAULT_PLUS_ICON)
    private var minusIcon: Drawable? = ContextCompat.getDrawable(context, DEFAULT_MINUS_ICON)
    private var textColor: Int = 0
    private var textSize: Int = DEFAULT_TEXT_SIZE

    private var displayingInteger = false
    private var releaseCallback: RepeatListener.ReleaseCallback? = null

    private var value: TextView? = null
    private var plusButton: ImageButton? = null
    private var minusButton: ImageButton? = null

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
        textSize = attributes.getDimension(R.styleable.HorizontalCounter_textSize, textSize.toFloat()).toInt()
        setTextColor(attributes.getColor(R.styleable.HorizontalCounter_textColor, textColor))

        currentValue = attributes.getString(R.styleable.HorizontalCounter_initialValue)?.toDouble() ?: DEFAULT_CURRENT_VALUE
        maxValue = attributes.getString(R.styleable.HorizontalCounter_maxValue)?.toDouble() ?: DEFAULT_MAX_VALUE
        minValue = attributes.getString(R.styleable.HorizontalCounter_minValue)?.toDouble() ?: -DEFAULT_MAX_VALUE
        setStepValue(attributes.getString(R.styleable.HorizontalCounter_stepValue)?.toDouble() ?: DEFAULT_STEP_COUNT)

        plusIcon = attributes.getDrawable(R.styleable.HorizontalCounter_plusIcon) ?: ContextCompat.getDrawable(context, DEFAULT_PLUS_ICON)
        minusIcon = attributes.getDrawable(R.styleable.HorizontalCounter_minusIcon) ?: ContextCompat.getDrawable(context, DEFAULT_MINUS_ICON)

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
        setupButtons()
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
            setImageDrawable(plusIcon)
            setPlusButtonColor(plusButtonColor)
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
            setMinusButtonColor(minusButtonColor)
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
        this.plusButton?.drawable?.let { DrawableCompat.setTint(it, plusButtonColor) }
    }

    fun getMinusButtonColor(): Int {
        return minusButtonColor
    }

    fun setMinusButtonColor(minusButtonColor: Int) {
        this.minusButtonColor = minusButtonColor
        this.minusButton?.drawable?.let { DrawableCompat.setTint(it, minusButtonColor) }
    }

    fun setPlusIcon(drawable: Drawable?) {
        this.plusButton?.setImageDrawable(drawable ?: ContextCompat.getDrawable(context, DEFAULT_PLUS_ICON));
        setPlusButtonColor(plusButtonColor)
    }

    fun setMinusIcon(drawable: Drawable?) {
        this.minusButton?.setImageDrawable(drawable ?: ContextCompat.getDrawable(context, DEFAULT_MINUS_ICON));
        setMinusButtonColor(minusButtonColor)
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
package com.gildaswise.horizontalcounter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
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

public class JavaHorizontalCounter extends LinearLayout {

    public static final int DEFAULT_STEP_COUNT_SIZE = 1;
    public static final int DEFAULT_TEXT_SIZE = 16;
    public static final Double DEFAULT_STEP_COUNT = 1.0;
    public static final Double DEFAULT_CURRENT_VALUE = 0.0;
    public static final Double DEFAULT_MAX_VALUE = 999.0;

    private Double stepValue = DEFAULT_STEP_COUNT;
    private Double currentValue = DEFAULT_CURRENT_VALUE;
    private Double maxValue = DEFAULT_MAX_VALUE;
    private Double minValue = -DEFAULT_MAX_VALUE;
    private int plusButtonColor;
    private int minusButtonColor;
    private int textColor;
    private int textSize = DEFAULT_TEXT_SIZE;
    private boolean displayingInteger = false;
    private RepeatListener.ReleaseCallback releaseCallback;
    private TextView value = findViewById(R.id.value);
    private Button plusButton, minusButton;

    public JavaHorizontalCounter(Context context) {
        super(context);
    }

    public JavaHorizontalCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        inflate(context, R.layout.layout, this);
        plusButton = findViewById(R.id.plus);
        minusButton = findViewById(R.id.minus);

        float density = getResources().getDisplayMetrics().density;
        plusButtonColor = ContextCompat.getColor(context, R.color.colorDefault);
        minusButtonColor = ContextCompat.getColor(context, R.color.colorDefault);
        textColor = ContextCompat.getColor(context, android.R.color.primary_text_light);
        textSize = (int) (density * textSize);

        if(attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HorizontalCounter);
            plusButtonColor = attributes.getColor(R.styleable.HorizontalCounter_plusButtonColor, plusButtonColor);
            minusButtonColor = attributes.getColor(R.styleable.HorizontalCounter_minusButtonColor, minusButtonColor);
            textColor = attributes.getColor(R.styleable.HorizontalCounter_textColor, textColor);
            textSize = (int) attributes.getDimension(R.styleable.HorizontalCounter_textSize, textSize);
            String currentValueString = attributes.getString(R.styleable.HorizontalCounter_initialValue);
            String stepValueString = attributes.getString(R.styleable.HorizontalCounter_stepValue);
            String maxValueString = attributes.getString(R.styleable.HorizontalCounter_maxValue);
            String minValueString = attributes.getString(R.styleable.HorizontalCounter_minValue);
            currentValue = (currentValueString != null) ? Double.valueOf(currentValueString) : DEFAULT_CURRENT_VALUE;
            stepValue = (stepValueString != null) ? Double.valueOf(stepValueString) : DEFAULT_STEP_COUNT;
            maxValue = (maxValueString != null) ? Double.valueOf(maxValueString) : DEFAULT_MAX_VALUE;
            minValue = (minValueString != null) ? Double.valueOf(minValueString) : -DEFAULT_MAX_VALUE;
            displayingInteger = attributes.getBoolean(R.styleable.HorizontalCounter_displayAsInteger, false);
            if(maxValue <= minValue || minValue >= maxValue) {throw new InvalidLimitsException();}
            attributes.recycle();
        }

        setupViews();

    }

    private void setupViews() {
        prepareViews(true);
        setupValueTextView();
        setupPlusButton();
        setupMinusButton();
        prepareViews(false);
    }

    private void setupButtons() {
        setupPlusButton();
        setupMinusButton();
    }

    private void setupValueTextView() {
        value.setTextColor(textColor);
        value.setTextSize(textSize);
        updateCurrentValue();
    }

    private void prepareViews(boolean initializing) {
        value.setVisibility((initializing) ? GONE : VISIBLE);
        plusButton.setVisibility((initializing) ? GONE : VISIBLE);
        minusButton.setVisibility((initializing) ? GONE : VISIBLE);
        value.setAlpha((initializing) ? 0 : 1f);
        plusButton.setAlpha((initializing) ? 0 : 1f);
        minusButton.setAlpha((initializing) ? 0 : 1f);
    }

    private void setupPlusButton() {
        plusButton.setTextColor(plusButtonColor);
        plusButton.setOnTouchListener(getPlusButtonListener());
    }

    private OnTouchListener getPlusButtonListener() {
        return new RepeatListener(v -> {
            if(currentValue < maxValue && (currentValue + stepValue) <= maxValue) {
                currentValue += stepValue;
                updateCurrentValue();
            }
        }, releaseCallback);
    }

    private void updateCurrentValue() {
        if(getCurrentValue() != null) {
            value.setText((isDisplayingInteger()) ?
                    String.format(Locale.US, "%d", getCurrentValue().intValue()) :
                    String.format("%." + getStepValueCount() + "f", getCurrentValue().floatValue()));
        }
    }

    private void setupMinusButton() {
        minusButton.setTextColor(minusButtonColor);
        minusButton.setOnTouchListener(getMinusButtonListener());
    }

    public void setOnReleaseListener(@NonNull RepeatListener.ReleaseCallback releaseCallback) {
        this.releaseCallback = releaseCallback;
        plusButton.setOnTouchListener(getPlusButtonListener());
        minusButton.setOnTouchListener(getMinusButtonListener());
        invalidate();
    }

    private OnTouchListener getMinusButtonListener() {
        return new RepeatListener(v -> {
            if(currentValue > minValue && (currentValue - stepValue) >= minValue) {
                currentValue -= stepValue;
                updateCurrentValue();
            }
        }, releaseCallback);
    }

    public Double getStepValue() {
        return stepValue;
    }

    public void setStepValue(Double stepValue) {
        this.stepValue = stepValue;
        Log.i("HorizontalCounter (set)", "stepValue: " + stepValue.toString());
        setupButtons();
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
        setupViews();
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
        if(maxValue <= getMinValue()) {throw new InvalidLimitsException();}
        setupButtons();
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        if(minValue >= getMaxValue()) {throw new InvalidLimitsException();}
        this.minValue = minValue;
        setupButtons();
    }

    public int getPlusButtonColor() {
        return plusButtonColor;
    }

    public void setPlusButtonColor(int plusButtonColor) {
        this.plusButtonColor = plusButtonColor;
        plusButton.setTextColor(plusButtonColor);
    }

    public int getMinusButtonColor() {
        return minusButtonColor;
    }

    public void setMinusButtonColor(int minusButtonColor) {
        this.minusButtonColor = minusButtonColor;
        minusButton.setTextColor(minusButtonColor);
    }

    public boolean isDisplayingInteger() {
        return displayingInteger;
    }

    public void setDisplayingInteger(boolean displayingInteger) {
        this.displayingInteger = displayingInteger;
        updateCurrentValue();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        value.setTextColor(textColor);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = (int) (textSize * getResources().getDisplayMetrics().density);
        value.setTextSize(this.textSize);
    }

    public int getStepValueCount() {
        return (getStepValue() != null) ? getStepValue().toString().split("\\.")[1].length() : DEFAULT_STEP_COUNT_SIZE;
    }
}

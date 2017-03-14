package com.gildaswise.horizontalcounter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Gildaswise on 01/03/2017.
 */

/**
 * Copyright 2017 - Gildásio Filho (@gildaswise)
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

public class HorizontalCounter extends LinearLayout {

    private Double stepValue = 1.0;
    private Double currentValue = 0.0;
    private Double maxValue = 999.0;
    private Double minValue = -maxValue;
    private int plusButtonColor;
    private int minusButtonColor;
    private int textColor;
    private int textSize = 16;
    private boolean displayingInteger = false;
    private RepeatListener.ReleaseCallback releaseCallback;
    private TextView value;
    private Button plusButton, minusButton;

    public HorizontalCounter(Context context) {
        super(context);
    }

    public HorizontalCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        inflate(context, R.layout.layout, this);
        value = (TextView) findViewById(R.id.value);
        plusButton = (Button) findViewById(R.id.plus);
        minusButton = (Button) findViewById(R.id.minus);

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
            currentValue = (double) attributes.getFloat(R.styleable.HorizontalCounter_initialValue, currentValue.floatValue());
            stepValue = (double) attributes.getFloat(R.styleable.HorizontalCounter_stepValue, stepValue.floatValue());
            maxValue = (double) attributes.getFloat(R.styleable.HorizontalCounter_maxValue, maxValue.floatValue());
            minValue = (double) attributes.getFloat(R.styleable.HorizontalCounter_minValue, minValue.floatValue());
            displayingInteger = attributes.getBoolean(R.styleable.HorizontalCounter_displayInteger, false);
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
        return new RepeatListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentValue < maxValue && (currentValue + stepValue) <= maxValue) {
                    currentValue += stepValue;
                    updateCurrentValue();
                }
            }}, releaseCallback);
    }

    private void updateCurrentValue() {
        if(getCurrentValue() != null) {value.setText(String.valueOf((isDisplayingInteger()) ? Integer.toString(getCurrentValue().intValue()) : getCurrentValue().floatValue()));}
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
        return new RepeatListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentValue > minValue && (currentValue - stepValue) >= minValue) {
                    currentValue -= stepValue;
                    updateCurrentValue();
                }
            }}, releaseCallback);
    }

    public Double getStepValue() {
        return stepValue;
    }

    public void setStepValue(Double stepValue) {
        this.stepValue = stepValue;
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
        this.minValue = minValue;
        if(minValue >= getMaxValue()) {throw new InvalidLimitsException();}
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
}

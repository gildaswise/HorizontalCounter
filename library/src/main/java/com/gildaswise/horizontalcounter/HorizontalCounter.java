package com.gildaswise.horizontalcounter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

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

public class HorizontalCounter extends LinearLayout {

    public static final String TAG = "JavaHorizontalCounter";

    public static final String DEFAULT_STEP_COUNT_SIZE = "1";
    public static final int DEFAULT_TEXT_SIZE = 16;
    public static final Double DEFAULT_STEP_COUNT = 1.0;
    public static final Double DEFAULT_CURRENT_VALUE = 0.0;
    public static final Double DEFAULT_MAX_VALUE = 999.0;

    private String stepString = DEFAULT_STEP_COUNT_SIZE;
    private Double stepValue = DEFAULT_STEP_COUNT;
    private Double currentValue = DEFAULT_CURRENT_VALUE;
    private Double maxValue = DEFAULT_MAX_VALUE;
    private Double minValue = -DEFAULT_MAX_VALUE;

    @ColorInt private int plusButtonColor;
    @ColorInt private int minusButtonColor;
    private int textColor;
    private int textSize = DEFAULT_TEXT_SIZE;

    private boolean displayingInteger = false;

    private RepeatListener.ReleaseCallback releaseCallback;
    private TextView value;
    private ImageButton plusButton, minusButton;
    private Drawable plusIcon, minusIcon;

    @DrawableRes private int DEFAULT_PLUS_ICON = R.drawable.plus;
    @DrawableRes private int DEFAULT_MINUS_ICON = R.drawable.minus;

    public HorizontalCounter(Context context) {
        super(context);
    }

    public HorizontalCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        inflate(context, R.layout.layout, this);

        plusButton = findViewById(R.id.plus);
        minusButton = findViewById(R.id.minus);
        value = findViewById(R.id.value);

        float density = getResources().getDisplayMetrics().density;
        textSize = (int) (density * textSize);

        plusButtonColor = ContextCompat.getColor(context, R.color.colorDefault);
        minusButtonColor = ContextCompat.getColor(context, R.color.colorDefault);
        textColor = ContextCompat.getColor(context, android.R.color.primary_text_light);

        if(attrs != null) {

            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HorizontalCounter);

            plusButtonColor = attributes.getColor(R.styleable.HorizontalCounter_plusButtonColor, plusButtonColor);
            minusButtonColor = attributes.getColor(R.styleable.HorizontalCounter_minusButtonColor, minusButtonColor);
            textSize = (int) attributes.getDimension(R.styleable.HorizontalCounter_textSize, textSize);
            setTextColor(attributes.getColor(R.styleable.HorizontalCounter_textColor, textColor));

            String currentValueString = attributes.getString(R.styleable.HorizontalCounter_initialValue);
            String stepValueString = attributes.getString(R.styleable.HorizontalCounter_stepValue);
            String maxValueString = attributes.getString(R.styleable.HorizontalCounter_maxValue);
            String minValueString = attributes.getString(R.styleable.HorizontalCounter_minValue);

            currentValue = (currentValueString != null) ? Double.valueOf(currentValueString) : DEFAULT_CURRENT_VALUE;
            maxValue = (maxValueString != null) ? Double.valueOf(maxValueString) : DEFAULT_MAX_VALUE;
            minValue = (minValueString != null) ? Double.valueOf(minValueString) : -DEFAULT_MAX_VALUE;
            setStepValue(stepValueString != null ? Double.valueOf(stepValueString) : DEFAULT_STEP_COUNT);

            Drawable rawPlusIcon = attributes.getDrawable(R.styleable.HorizontalCounter_plusIcon);
            Drawable rawMinusIcon = attributes.getDrawable(R.styleable.HorizontalCounter_minusIcon);

            plusIcon = (rawPlusIcon != null) ? rawPlusIcon : ContextCompat.getDrawable(context, DEFAULT_PLUS_ICON);
            minusIcon = (rawMinusIcon != null) ? rawMinusIcon : ContextCompat.getDrawable(context, DEFAULT_MINUS_ICON);

            displayingInteger = attributes.getBoolean(R.styleable.HorizontalCounter_displayAsInteger, false);

            if(maxValue <= minValue || minValue >= maxValue) { throw new InvalidLimitsException(); }

            attributes.recycle();
        }

        setupViews();

    }

    private void setupViews() {
        prepareViews(true);
        setupValueTextView();
        setupButtons();
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

    private void updateCurrentValue() {
        if(getCurrentValue() != null) {
            value.setText((isDisplayingInteger()) ?
                    String.format(Locale.US, "%d", getCurrentValue().intValue()) :
                    String.format("%." + getStepString() + "f", getCurrentValue().floatValue()));
        }
    }

    private void setupPlusButton() {
        setPlusButtonColor(plusButtonColor);
        plusButton.setImageDrawable(plusIcon);
        plusButton.setOnTouchListener(getPlusButtonListener());
    }

    public int getPlusButtonColor() {
        return plusButtonColor;
    }

    public void setPlusButtonColor(int plusButtonColor) {
        this.plusButtonColor = plusButtonColor;
        DrawableCompat.setTint(this.plusButton.getDrawable(), plusButtonColor);
    }

    public Drawable getPlusIcon() {
        return plusIcon;
    }

    public void setPlusIcon(Drawable plusIcon) {
        this.plusIcon = (plusIcon != null) ? plusIcon : ContextCompat.getDrawable(getContext(), DEFAULT_PLUS_ICON);
        this.plusButton.setImageDrawable(this.plusIcon);
        setPlusButtonColor(this.plusButtonColor);
    }

    private OnTouchListener getPlusButtonListener() {
        return new RepeatListener(v -> {
            if(getCurrentValue() < getMaxValue() && (getCurrentValue() + getStepValue()) <= getMaxValue()) {
                setCurrentValue(getCurrentValue() + getStepValue());
                updateCurrentValue();
            }
        }, getReleaseCallback());
    }

    private void setupMinusButton() {
        setMinusButtonColor(minusButtonColor);
        minusButton.setImageDrawable(minusIcon);
        minusButton.setOnTouchListener(getMinusButtonListener());
    }

    public int getMinusButtonColor() {
        return minusButtonColor;
    }

    public void setMinusButtonColor(int minusButtonColor) {
        this.minusButtonColor = minusButtonColor;
        DrawableCompat.setTint(this.minusButton.getDrawable(), minusButtonColor);
    }

    public Drawable getMinusIcon() {
        return minusIcon;
    }

    public void setMinusIcon(Drawable minusIcon) {
        this.minusIcon = (minusIcon != null) ? minusIcon : ContextCompat.getDrawable(getContext(), DEFAULT_MINUS_ICON);
        this.minusButton.setImageDrawable(this.minusIcon);
        setMinusButtonColor(this.minusButtonColor);
    }

    private OnTouchListener getMinusButtonListener() {
        return new RepeatListener(v -> {
            if(getCurrentValue() > getMinValue() && (getCurrentValue() - getStepValue()) >= getMinValue()) {
                setCurrentValue(getCurrentValue() - getStepValue());
                updateCurrentValue();
            }
        }, getReleaseCallback());
    }

    public void setOnReleaseListener(@NonNull RepeatListener.ReleaseCallback releaseCallback) {
        this.releaseCallback = releaseCallback;
    }

    public RepeatListener.ReleaseCallback getReleaseCallback() {
        return releaseCallback;
    }

    public String getStepString() { return stepString; }

    public Double getStepValue() {
        return stepValue;
    }

    public void setStepValue(@NonNull Double stepValue) {
        this.stepValue = stepValue;
        this.stepString = String.valueOf(stepValue.toString().split("\\.")[1].length());
        updateCurrentValue();
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(@NonNull Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(@NonNull Double maxValue) {
        if(maxValue <= getMinValue()) { throw new InvalidLimitsException(); }
        this.maxValue = maxValue;
        setupButtons();
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(@NonNull Double minValue) {
        if(minValue >= getMaxValue()) { throw new InvalidLimitsException(); }
        this.minValue = minValue;
        setupButtons();
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

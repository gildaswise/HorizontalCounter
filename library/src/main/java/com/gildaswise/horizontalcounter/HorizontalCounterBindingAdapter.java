package com.gildaswise.horizontalcounter;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.Drawable;

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

@BindingMethods({
    @BindingMethod(type = HorizontalCounter.class, attribute = "initialValue", method = "setCurrentValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "stepValue", method = "setStepValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "maxValue", method = "setMaxValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "minValue", method = "setMinValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "textColor", method = "setPlusColor"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "textSize", method = "setTextSize"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "minusButtonColor", method = "setMinusButtonColor"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "minusIcon", method = "setMinusIcon"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "plusButtonColor", method = "setPlusButtonColor"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "plusIcon", method = "setPlusIcon"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "displayAsInteger", method = "setDisplayingInteger"),
})
public class HorizontalCounterBindingAdapter {

    @BindingAdapter("app:initialValue")
    public static void setCurrentValue(HorizontalCounter view, Double value) {
        view.setCurrentValue((value != null) ? value : HorizontalCounter.DEFAULT_CURRENT_VALUE);
    }

    @BindingAdapter("app:stepValue")
    public static void setStepValue(HorizontalCounter view, Double value) {
        view.setStepValue((value != null) ? value : HorizontalCounter.DEFAULT_STEP_COUNT);
    }

    @BindingAdapter("app:maxValue")
    public static void setMaxValue(HorizontalCounter view, Double value) {
        view.setMaxValue((value != null) ? value : HorizontalCounter.DEFAULT_MAX_VALUE);
    }

    @BindingAdapter("app:minValue")
    public static void setMinValue(HorizontalCounter view, Double value) {
        view.setMinValue((value != null) ? value : -HorizontalCounter.DEFAULT_MAX_VALUE);
    }

    @BindingAdapter("app:textColor")
    public static void setTextColor(HorizontalCounter view, int color) {
        view.setTextColor(color);
    }

    @BindingAdapter("app:textSize")
    public static void setTextSize(HorizontalCounter view, int size) {
        view.setTextSize(size);
    }

    @BindingAdapter("app:minusButtonColor")
    public static void setMinusButtonColor(HorizontalCounter view, int color) {
        view.setMinusButtonColor(color);
    }

    @BindingAdapter("app:plusButtonColor")
    public static void setPlusButtonColor(HorizontalCounter view, int color) {
        view.setPlusButtonColor(color);
    }

    @BindingAdapter("app:plusIcon")
    public static void setPlusIcon(HorizontalCounter view, Drawable value) {
        view.setPlusIcon(value);
    }

    @BindingAdapter("app:minusIcon")
    public static void setMinusIcon(HorizontalCounter view, Drawable value) {
        view.setMinusIcon(value);
    }

    @BindingAdapter("app:displayAsInteger")
    public static void setDisplayingInteger(HorizontalCounter view, boolean value) {
        view.setDisplayingInteger(value);
    }

}

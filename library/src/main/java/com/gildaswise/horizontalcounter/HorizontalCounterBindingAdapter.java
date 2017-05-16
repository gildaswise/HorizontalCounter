package com.gildaswise.horizontalcounter;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;

/**
 * Created by Gildaswise on 11/05/2017.
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

@BindingMethods({
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:initialValue", method = "setCurrentValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:stepValue", method = "setStepValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:maxValue", method = "setMaxValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:minValue", method = "setMinValue"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:textColor", method = "setTextColor"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:textSize", method = "setTextSize"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:minusButtonColor", method = "setMinusButtonColor"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:plusButtonColor", method = "setPlusButtonColor"),
    @BindingMethod(type = HorizontalCounter.class, attribute = "app:displayAsInteger", method = "setDisplayingInteger"),
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

    @BindingAdapter("app:displayAsInteger")
    public static void setDisplayingInteger(HorizontalCounter view, boolean value) {
        view.setDisplayingInteger(value);
    }

}

package com.gildaswise.horizontalcounter;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;

/**
 * Created by Gildaswise on 11/05/2017.
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
        view.setCurrentValue(value);
    }

    @BindingAdapter("app:stepValue")
    public static void setStepValue(HorizontalCounter view, Double value) {
        view.setStepValue(value);
    }

    @BindingAdapter("app:maxValue")
    public static void setMaxValue(HorizontalCounter view, Double value) {
        view.setMaxValue(value);
    }

    @BindingAdapter("app:minValue")
    public static void setMinValue(HorizontalCounter view, Double value) {
        view.setMinValue(value);
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

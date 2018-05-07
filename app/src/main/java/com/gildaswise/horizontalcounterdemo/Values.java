package com.gildaswise.horizontalcounterdemo;

public class Values {

    Double value;
    Double step;
    Double max;
    Double min;

    public Values(Double value, Double step, Double max, Double min) {
        this.value = value;
        this.step = step;
        this.max = max;
        this.min = min;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }
}

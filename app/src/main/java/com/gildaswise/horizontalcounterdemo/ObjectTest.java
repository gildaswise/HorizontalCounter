package com.gildaswise.horizontalcounterdemo;

/**
 * Created by Gildaswise on 16/05/2017.
 */

public class ObjectTest {

    private Double step;
    private Double value;
    private Double max;
    private Double min;

    public ObjectTest(Double step, Double value, Double max, Double min) {
        this.step = step;
        this.value = value;
        this.max = max;
        this.min = min;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

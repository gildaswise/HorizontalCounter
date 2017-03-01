# HorizontalCounter

![Screenshot](https://raw.githubusercontent.com/gildaswise/HorizontalCounter/master/art/screenshot.png)

An amazingly simple and easy-to-use library for your counter (or number picker) needs! It started as a Ctrl-C, Ctrl-V code between my projects and now I've decided to make it into a open-source library!

## Demo

![GIF](https://raw.githubusercontent.com/gildaswise/HorizontalCounter/master/art/demo.gif)

## Use it now! (via Gradle)

```java
dependencies {
    ...
    compile 'com.gildaswise:horizontalcounter:1.0.0'
}
```

## Usage

* In XML layout:

```xml
<com.gildaswise.horizontalcounter.HorizontalCounter
    android:id="@+id/horizontal_counter"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:stepValue="1"
    app:textColor="#009688"
    app:minusButtonColor="#F44336"
    app:plusButtonColor="#2196F3"
    app:initialValue="1"
    app:maxValue="100"
    app:minValue="-100" />
```

* In Java:

```java
HorizontalCounter horizontalCounter = (HorizontalCounter) findViewById(R.id.horizontal_counter);

//Value of increasing/decreasing
horizontalCounter.setStepValue(1); 
//Obviously, the maximum value
horizontalCounter.setMaxValue(100);
//Same as above (but minimum)
horizontalCounter.setMinValue(-100);
//The current value is the one displayed on the view, you can set its starting value here, or via XML above
horizontalCounter.setCurrentValue(1);
//As the name says, this will make the onRelease() code run after any of the buttons is released, either in a long press, or single press
horizontalCounter.setOnReleaseListener(new RepeatListener.ReleaseCallback() {
    @Override
    public void onRelease() {
        Toast.makeText(MainActivity.this, "Value updated to: " + horizontalCounter.getCurrentValue(), Toast.LENGTH_SHORT).show();
    }
});
//You can also set each color via Java (using int color values), and also value's textSize
horizontalCounter.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
horizontalCounter.setMinusButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));
horizontalCounter.setPlusButtonColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
horizontalCounter.setTextSize(16);

```

## Sample

* Just clone the repository and check the *app* module!

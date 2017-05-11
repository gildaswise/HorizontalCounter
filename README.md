# HorizontalCounter
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://jitpack.io/v/gildaswise/HorizontalCounter.svg)](https://jitpack.io/#gildaswise/HorizontalCounter)

An amazingly simple and easy-to-use library for your counter (or number picker) needs! It started as a Ctrl-C, Ctrl-V code between my projects and now I've decided to make it into a open-source library!

## Demo

![GIF](https://raw.githubusercontent.com/gildaswise/HorizontalCounter/master/art/demo.gif)

## First, add JitPack to your project's build.gradle (outside of app folder)

```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## Then add this in your app's build.gradle dependencies

```java
dependencies {
    ...
    compile 'com.github.gildaswise:HorizontalCounter:1.0.5'
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
    app:textSize="16dp"
    app:minusButtonColor="#F44336"
    app:plusButtonColor="#2196F3"
    app:displayAsInteger="false"
    app:initialValue="1"
    app:maxValue="100"
    app:minValue="-100" />
```

* In Java:

```java
HorizontalCounter horizontalCounter = (HorizontalCounter) findViewById(R.id.horizontal_counter);

//Value of increasing/decreasing
horizontalCounter.setStepValue(1.0); 
//Obviously, the maximum value
horizontalCounter.setMaxValue(100.0);
//Same as above (but minimum)
horizontalCounter.setMinValue(-100.0);
//The current value is the one displayed on the view, you can set its starting value here, or via XML above
horizontalCounter.setCurrentValue(1.0);
// If you want to display as integer just do this:
horizontalCounter.setDisplayingInteger(true);
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


## License

> Copyright 2017 - Gildásio Filho (@gildaswise)
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
> http://www.apache.org/licenses/LICENSE-2.0
> 
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
 
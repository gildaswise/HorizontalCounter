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
    compile 'com.github.gildaswise:HorizontalCounter:1.3.0'
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
// Params customization:
horizontalCounter.setStepValue(0.01);
horizontalCounter.setMaxValue(100.0);
horizontalCounter.setMinValue(-100.0);
horizontalCounter.setCurrentValue(1.0);

// If you want to display as integer just do this:
// horizontalCounter.setDisplayingInteger(true)

// View customization:
horizontalCounter.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
horizontalCounter.setMinusIcon(ContextCompat.getDrawable(this, R.drawable.chevron_down));
horizontalCounter.setMinusButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));
horizontalCounter.setPlusIcon(ContextCompat.getDrawable(this, R.drawable.chevron_up));
horizontalCounter.setPlusButtonColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
horizontalCounter.setTextSize(16);

// Custom action on release:
horizontalCounter.setOnReleaseListener(() -> {
    Toast.makeText(this, "Value updated to: " + horizontalCounter.getCurrentValue(), Toast.LENGTH_SHORT).show();
});
```

### You can also use it with Data Binding! (Click [here](https://github.com/gildaswise/HorizontalCounter/blob/master/app/src/main/java/com/gildaswise/horizontalcounterdemo/MainActivityDataBinding.kt) for an example!)

## Sample

* Just clone the repository and check the *app* module!

## License

> Copyright 2018 - Gildásio Filho - @gildaswise
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

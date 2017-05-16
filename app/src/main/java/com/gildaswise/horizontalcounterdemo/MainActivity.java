package com.gildaswise.horizontalcounterdemo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gildaswise.horizontalcounter.HorizontalCounter;
import com.gildaswise.horizontalcounter.RepeatListener;

public class MainActivity extends AppCompatActivity {

    private HorizontalCounter horizontalCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalCounter = (HorizontalCounter) findViewById(R.id.horizontal_counter);
        horizontalCounter.setStepValue(0.01);
        horizontalCounter.setMaxValue(100.0);
        horizontalCounter.setMinValue(-100.0);
        horizontalCounter.setCurrentValue(1.0);
        // If you want to display as integer just do this:
        horizontalCounter.setDisplayingInteger(true);
        horizontalCounter.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        horizontalCounter.setMinusButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));
        horizontalCounter.setPlusButtonColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        horizontalCounter.setTextSize(16);

        horizontalCounter.setOnReleaseListener(new RepeatListener.ReleaseCallback() {
            @Override
            public void onRelease() {
            Toast.makeText(MainActivity.this, "Value updated to: " + horizontalCounter.getCurrentValue(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

package com.gildaswise.horizontalcounterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        horizontalCounter.setMaxValue(120);
        horizontalCounter.setMinValue(-20);
        horizontalCounter.setStepValue(20);
        horizontalCounter.setOnReleaseListener(new RepeatListener.ReleaseCallback() {
            @Override
            public void onRelease() {
                Toast.makeText(MainActivity.this, "Value updated to: " + horizontalCounter.getCurrentValue(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

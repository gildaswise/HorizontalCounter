package com.gildaswise.horizontalcounterdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gildaswise.horizontalcounter.HorizontalCounter;

public class MainActivity extends AppCompatActivity {

    private HorizontalCounter horizontalCounter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalCounter = findViewById(R.id.horizontal_counter);

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

    }
}

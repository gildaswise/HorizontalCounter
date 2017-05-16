package com.gildaswise.horizontalcounterdemo;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gildaswise.horizontalcounter.HorizontalCounter;
import com.gildaswise.horizontalcounter.RepeatListener;
import com.gildaswise.horizontalcounterdemo.databinding.ActivityMainDatabindingBinding;

public class MainActivityDataBinding extends AppCompatActivity {

    private ActivityMainDatabindingBinding binding;
    private HorizontalCounter horizontalCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_databinding);
        binding.setObject(new ObjectTest(0.1, 1.0, 10.0, -10.0));
        binding.horizontalCounter.setOnReleaseListener(new RepeatListener.ReleaseCallback() {
            @Override
            public void onRelease() {
                Toast.makeText(MainActivityDataBinding.this, "Value updated to: " + binding.horizontalCounter.getCurrentValue(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

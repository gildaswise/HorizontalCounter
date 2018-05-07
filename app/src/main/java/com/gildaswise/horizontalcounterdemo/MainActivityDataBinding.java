package com.gildaswise.horizontalcounterdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gildaswise.horizontalcounterdemo.databinding.ActivityMainDatabindingBinding;

public class MainActivityDataBinding extends AppCompatActivity {

    private ActivityMainDatabindingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_databinding);
        binding.setValues(new Values(1.0, 1.0, 10.0, -10.0));
        binding.horizontalCounter.setOnReleaseListener(() -> {
            Toast.makeText(this, "Value updated to: " + binding.horizontalCounter.getCurrentValue(), Toast.LENGTH_SHORT).show();
        });
    }
}

package com.gildaswise.horizontalcounterdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gildaswise.horizontalcounter.RepeatListener

import com.gildaswise.horizontalcounterdemo.databinding.ActivityMainDatabindingBinding

class MainActivityDataBinding : AppCompatActivity() {

    private val binding: ActivityMainDatabindingBinding by lazy { DataBindingUtil.setContentView<ActivityMainDatabindingBinding>(this, R.layout.activity_main_databinding) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.values = Values(0.1, 1.0, 10.0, -10.0)
        binding.horizontalCounter.setOnReleaseListener(RepeatListener.ReleaseCallback {
            Toast.makeText(this@MainActivityDataBinding, "Value updated to: " + binding.horizontalCounter.getCurrentValue()!!, Toast.LENGTH_SHORT).show()
        })
    }
}

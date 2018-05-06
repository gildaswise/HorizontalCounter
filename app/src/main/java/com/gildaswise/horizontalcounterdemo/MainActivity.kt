package com.gildaswise.horizontalcounterdemo

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.gildaswise.horizontalcounter.HorizontalCounter
import com.gildaswise.horizontalcounter.RepeatListener

class MainActivity : AppCompatActivity() {

    private var horizontalCounter: HorizontalCounter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        horizontalCounter = findViewById(R.id.horizontal_counter)

        // Params customization:
        horizontalCounter?.apply {
            setStepValue(0.01)
            setMaxValue(100.0)
            setMinValue(-100.0)
            setCurrentValue(1.0)
        }

        // If you want to display as integer just do this:
        // horizontalCounter?.setDisplayingInteger(true)

        // View customization:
        horizontalCounter?.apply {
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
            setMinusButtonColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
            setPlusButtonColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark))
            setTextSize(16)
        }

        // Custom action on release:
        horizontalCounter?.setOnReleaseListener(RepeatListener.ReleaseCallback {
            Toast.makeText(this@MainActivity, "Value updated to: " + horizontalCounter?.getCurrentValue(), Toast.LENGTH_SHORT).show()
        })

    }
}

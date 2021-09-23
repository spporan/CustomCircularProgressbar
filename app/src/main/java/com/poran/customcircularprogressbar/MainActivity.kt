package com.poran.customcircularprogressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.poran.customcircularprogressbar.component.CircularProgress

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var default = 60;

        val progressView = findViewById<CircularProgress>(R.id.progress_circular)
        progressView.progressValue = default

        findViewById<Button>(R.id.progress_btn).setOnClickListener {
            default += 4
            if (default > 100) return@setOnClickListener
            progressView.progressValue = default
        }
    }
}
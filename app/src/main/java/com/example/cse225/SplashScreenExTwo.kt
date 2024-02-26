package com.example.cse225

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class SplashScreenExTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_ex_two)
    }
    override fun onBackPressed() {
        Toast.makeText(applicationContext,"Hello",Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }
}
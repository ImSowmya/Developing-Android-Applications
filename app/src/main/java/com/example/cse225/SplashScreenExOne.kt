package com.example.cse225

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreenExOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_ex_one)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val i = Intent(this, SplashScreenExTwo::class.java)
                startActivity(i)
                finish() },5000 )

    }

}
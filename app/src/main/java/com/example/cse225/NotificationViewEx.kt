package com.example.cse225


import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.cse225.databinding.ActivityNotificationViewExBinding

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class NotificationViewEx : AppCompatActivity() {

    lateinit var notificationManager : NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_view_ex)

        var txtView = findViewById<TextView>(R.id.tv)

        var inp = NotificationManagerEx()

        txtView.text = "Hey!"

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        notificationManager.cancel(inp.notificationId)

    }
}
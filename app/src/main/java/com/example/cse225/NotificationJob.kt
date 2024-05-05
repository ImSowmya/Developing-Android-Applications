package com.example.cse225

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.RemoteViews
import android.widget.Toast

lateinit var notificationManager : NotificationManager
lateinit var notificationChannel : NotificationChannel
lateinit var builder : Notification.Builder
lateinit var btnNotify: Button
lateinit var remoteCollapsedViews: RemoteViews
lateinit var remoteExpandedViews: RemoteViews
lateinit var pendingIntent: PendingIntent
lateinit var soundUri: Uri
lateinit var audioAttr: AudioAttributes
// lateinit var remoteInput: RemoteInput
private val channelId = "My Channel Id"
private val description = "Test Notification"
private val title = "Notification"

//  val myKey = "Remote Key"
val notificationId = 1234
class NotificationJob:JobService()
{

    override fun onStartJob(p0: JobParameters?): Boolean
    {
        val intent = Intent(this,NotificationViewEx::class.java)
        pendingIntent = PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_IMMUTABLE)

        soundUri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+
                    applicationContext.packageName+"/"+R.raw.ringtone)
        audioAttr = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        remoteCollapsedViews = RemoteViews(packageName, R.layout.activity_splash_screen_ex_one)
        remoteExpandedViews = RemoteViews(packageName, R.layout.activity_splash_screen_ex_two)

        myNotificationChannel()

        notificationManager.notify(notificationId,builder.build())
        return true
    }

    private fun myNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationChannel.setSound(soundUri,audioAttr)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_baseline_announcement)
                .setContentTitle(title)
                .setContentText(description)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.logo_toolbar))
                .setContentIntent(pendingIntent)

                .setCustomContentView(remoteCollapsedViews)
                .setCustomBigContentView(remoteExpandedViews)

                .setAutoCancel(true)

        }

        else{
            builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_baseline_announcement)
                .setContentTitle(title)
                .setContentText(description)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.logo_toolbar))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
    }
    override fun onStopJob(p0: JobParameters?): Boolean
    {
        Log.d("TAG","onStopJob:")
        return true
    }
}
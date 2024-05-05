package com.example.cse225

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ContentResolver
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

const val CHANNEL_ID= "My Channel Id"
class CA2JobServiceNotification: JobService()
{
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
    val description = "Test Notification"
    val title = "Notification"

    //  val myKey = "Remote Key"
    val notificationId = 1234
    override fun onStartJob(p0: JobParameters?): Boolean
    {
        Log.d("TAG", "onStartJob:")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this@CA2JobServiceNotification, NotificationViewEx::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this@CA2JobServiceNotification, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

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

        notificationManager.notify(notificationId,buildNotification(pendingIntent, soundUri, audioAttr, remoteCollapsedViews, remoteExpandedViews))
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean
    {
        Log.d("TAG","onStopJob:")
        return true
    }

    private fun myNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(CHANNEL_ID,description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationChannel.setSound(soundUri,audioAttr)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, CHANNEL_ID)
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

    private fun buildNotification(
        pendingIntent: PendingIntent,
        soundUri: Uri,
        audioAttributes: AudioAttributes,
        remoteCollapsedViews: RemoteViews,
        remoteExpandedViews: RemoteViews
    ): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For Android Oreo and above, use Notification.Builder with a channel
            Notification.Builder(this, CHANNEL_ID)
        } else {
            // For versions below Oreo, use Notification.Builder without a channel
            Notification.Builder(this)
        }
            .setSmallIcon(R.drawable.ic_baseline_announcement)
            .setContentTitle("Your Notification Title")
            .setContentText("Your Notification Text")
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo_toolbar))
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteCollapsedViews)
            .setCustomBigContentView(remoteExpandedViews)
            .setSound(soundUri, audioAttributes)
            .setAutoCancel(true)
            .build()
    }

}
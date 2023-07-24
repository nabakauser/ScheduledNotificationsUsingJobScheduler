package com.example.jobscheduler

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat


class JobSchedulerService : JobService() {
    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        Log.d("JOBSERV", "beforeNotif - onStartJob() - ${System.currentTimeMillis()}")
        val notification = createNotification()
        displayNotification(notification)
        return true
    }

    override fun onStopJob(jobParameters: JobParameters?): Boolean {
        Log.d("JOBSERV", "onStopJob()")
        return true
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notificationChannel = NotificationChannel(
                "notificationChannelId", // notification channel id
                "notificationChannelName",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Notification.Builder(this, "notificationChannelId") //channel id must be same as your notification channel id
        else Notification.Builder(this)

        return builder
            .setContentTitle("Job Scheduler Notification")
            .setContentText("Yaayy!! Your notification is here!!!")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build()
    }

    @SuppressLint("MissingPermission")
    private fun displayNotification(notification: Notification) {
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(123, notification)
    }
}
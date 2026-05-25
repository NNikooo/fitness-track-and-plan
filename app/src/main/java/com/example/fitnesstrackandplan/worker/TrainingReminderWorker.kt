package com.example.fitnesstrackandplan.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.fitnesstrackandplan.R

// notifikacie s pomocou A I
class TrainingReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        createNotificationChannel()
        showNotification()
        return Result.success()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "training_reminder_channel",
            "Training reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(
            applicationContext,
            "training_reminder_channel"
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Fitness Track & Plan")
            .setContentText("Check your planned trainings for today.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat
            .from(applicationContext)
            .notify(1, notification)
    }
}
package com.raza.householdrecharge.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object AppNotificationManager {

    private const val CHANNEL_ID = "recharge"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.recharge),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.createNotificationChannel(channel)
    }

    fun show(
        context: Context,
        notificationId: Int,
        title: String,
        text: String
    ) {
        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(notificationId, notification)
    }
}
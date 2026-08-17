package com.raza.householdrecharge.notification

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.raza.householdrecharge.R

object RechargeNotification {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showRequest(
        context: Context,
        memberName: String
    ) {
        AppNotificationManager.show(
            context = context,
            notificationId = memberName.hashCode(),
            title = context.getString(R.string.recharge),
            text = memberName
        )
    }
}
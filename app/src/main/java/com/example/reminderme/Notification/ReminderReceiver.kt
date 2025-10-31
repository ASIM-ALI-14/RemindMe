package com.example.reminderme.Notification



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Reminder"
        val dateTime = intent.getStringExtra("dateTime") ?: ""

        NotificationHelper.showNotification(context, title, "It’s time! $dateTime")
    }
}

package com.example.reminderme.Notification



import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.*

object NotificationScheduler {

    fun scheduleReminder(context: Context, title: String, date: String, time: String) {
        try {
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateTime = format.parse("$date $time") ?: return

            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("title", title)
                putExtra("dateTime", "$date $time")
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                dateTime.time.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                dateTime.time,
                pendingIntent
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

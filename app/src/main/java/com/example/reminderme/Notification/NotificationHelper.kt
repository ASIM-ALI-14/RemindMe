import android.Manifest
import android.R
import android.R.attr.priority
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    private const val CHANNEL_ID = "reminder_channel"

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, title: String, string: String) {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Intent to open app when notification is tapped
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Beautiful high-priority notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dialog_info) // or your own icon
            .setContentTitle("🔔 Reminder: $title")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Priority: ${priority.toString()}")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Show at top
            .setAutoCancel(true) // Dismiss when tapped
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
            .build()

        // Show notification
        NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), notification)
    }
}

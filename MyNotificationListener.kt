package com.myJib.notifrepost

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class MyNotificationListener : NotificationListenerService() {
  override fun onNotificationPosted(sbn: StatusBarNotification) {
    if (sbn.packageName != "com.whatsapp") return
    val text = sbn.notification.extras
                  .getCharSequence("android.text")?.toString() ?: return
    repost(text)
  }

  private fun repost(message: String) {
    val channelId = "wa_listener"
    val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      nm.createNotificationChannel(
        NotificationChannel(channelId, "WA Repost", NotificationManager.IMPORTANCE_HIGH))
    }
    val notif = NotificationCompat.Builder(this, channelId)
      .setContentTitle("WA Terakhir")
      .setContentText(message)
      .setSmallIcon(android.R.drawable.sym_action_chat)
      .setAutoCancel(true)
      .build()
    nm.notify(1001, notif)
  }
}

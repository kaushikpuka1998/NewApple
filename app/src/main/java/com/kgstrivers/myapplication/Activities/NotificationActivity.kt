package com.kgstrivers.myapplication.Activities
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kgstrivers.myapplication.R
import kotlinx.android.synthetic.main.activity_notification.*


class NotificationActivity : AppCompatActivity() {

    val CHANNEL_ID = "Notification"
    val NOTIFY_ID = 101
    val TAG = "NotificationActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)




        notificationbutton.setOnClickListener {

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                val notificationtitlestring = notificationtitle.text.toString()
                val notificationdetailsString = notificationdetails.text.toString()
                Log.d(TAG,"ENTERED Pressed button")
                Log.d(TAG,notificationtitlestring)
                Log.d(TAG,notificationdetailsString)
                sendNotification(notificationtitlestring,notificationdetailsString)
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(a1:String, a2:String)
    {

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.apartment2x)
            .setContentTitle(a1)
            .setContentText(a2)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "NewApple"
            val descriptionText ="GGGGG"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFY_ID, builder.build())
            Log.d(TAG,"NOTIFY DONE")
        }
    }
}
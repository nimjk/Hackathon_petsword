package com.kisia.pets_word

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.core.app.NotificationManagerCompat

class NotificationTest : AppCompatActivity() {

    private val channelid = "Petsword1"   // Channel for notification
    private lateinit var notificationManager: NotificationManager
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_test)
        db = FirebaseFirestore.getInstance()
        createNotificationChannel(channelid, "Pets_word", "this is a test Channel")

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            db.collection("35.223.237.205")
                .whereEqualTo("allowance", "deny")
                .whereEqualTo("temporary", "Y")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("Firebase", "${document.id} => ${document.data}")
                        displayNotification()
                    }

                }
                .addOnFailureListener { exception ->
                    Log.w("Firebase", "Error getting documents: ", exception)
                }
        }
    }

    private fun displayNotification() {
        val notificationId = 45
        val intent = Intent(this, AllowanceWork::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = Notification.Builder(this, channelid)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Warning!")
            .setContentText("Someone try to connect Webcam!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = channelDescription
        }
        // Register the channel with the system
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
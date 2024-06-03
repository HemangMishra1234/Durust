package com.tripod.durust

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReciever: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("Extra_Message")?: return
         println("Alarm Message $message")
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        if (context != null) {
            navigate(context)
        }else
            println("Failed to open app due to context")
    }
}

fun navigate(context: Context){
    val packageName = "com.whatsapp"
    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        try {
            context.startActivity(intent)
        }catch (e: Exception){
            Toast.makeText(context, "Error launching WhatsApp: $e", Toast.LENGTH_LONG).show()
            Log.e("Navigation error", "$e")
        }
    } else {
        Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_LONG).show()
    }
}
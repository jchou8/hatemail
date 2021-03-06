package edu.washington.info448.hatemail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message")
        val phone = intent.getStringExtra("phone")

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            phone,
            null,
            message,
            null,
            null
        )

        Log.i("MESSAGE_RECEIVER", "sending %s to %s".format(message, phone))

        val history = History(message, Date(), phone)
        val appSharedPrefs = context.getSharedPreferences("prefs", 0)
        val prefsEditor = appSharedPrefs.edit()
        var curJson = appSharedPrefs.getString("Histories", "")
        val gson = Gson()
        val type = object : TypeToken<List<History>>() {
        }.type
        var curHistories: MutableList<History>? = gson.fromJson(curJson, type)
        Log.i("FUCK", curHistories.toString())
        if (curHistories == null) {
            curHistories = mutableListOf(history)
        } else {
            curHistories.add(history)
        }
        prefsEditor.putString("Histories", gson.toJson(curHistories))
        prefsEditor.commit()
    }
}

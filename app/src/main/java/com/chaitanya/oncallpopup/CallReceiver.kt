package com.chaitanya.oncallpopup

// CallReceiver.kt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.telephony.TelephonyManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView


class CallReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context, intent: Intent) {

        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        when (state) {
            TelephonyManager.EXTRA_STATE_RINGING -> {
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                // You can use incomingNumber to get the phone number and perform any other checks
                if (incomingNumber!= null){
                    startPopupService(context, incomingNumber.toString()) // Replace with the actual contact name
                }

            }
            TelephonyManager.EXTRA_STATE_IDLE -> {
                stopPopupService(context)
            }
        }

    }
    private fun startPopupService(context: Context, contactName: String) {
        val serviceIntent = Intent(context, PopupService::class.java)
        serviceIntent.putExtra("INCOMING_NUMBER", contactName)
        context.startService(serviceIntent)
    }

    private fun stopPopupService(context: Context) {
        val serviceIntent = Intent(context, PopupService::class.java)
        context.stopService(serviceIntent)
    }


}

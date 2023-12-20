package com.chaitanya.oncallpopup

// PopupService.kt

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class PopupService : Service() {

    private var wm: WindowManager? = null
    private var ly1: View? = null
    private var params1: WindowManager.LayoutParams? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val incomingNumber = intent.getStringExtra("INCOMING_NUMBER") ?: ""
            showPopup(incomingNumber)
        }
        return START_STICKY
    }

    private fun showPopup(contactName: String) {
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        params1 = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY or
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSPARENT
        )

        params1!!.gravity = Gravity.CENTER
        params1!!.height = 320
        params1!!.width = 1000
        params1!!.format = PixelFormat.TRANSLUCENT
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        ly1 = inflater.inflate(R.layout.popup_layout, null)
        val textView = ly1?.findViewById<TextView>(R.id.popupTextView)
        textView?.text = "Incoming call from ${contactName}"
        textView?.setOnClickListener {
            wm!!.removeView(ly1)
        }
        wm!!.addView(ly1, params1)
    }

    override fun onDestroy() {
        super.onDestroy()
        wm?.removeView(ly1)
    }
}

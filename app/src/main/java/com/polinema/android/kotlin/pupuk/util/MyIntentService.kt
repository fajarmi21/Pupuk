package com.polinema.android.kotlin.pupuk.util

import android.app.IntentService
import android.content.Intent
import android.util.Log

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions and extra parameters.
 */
class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.i("TAG", "Intent Service started")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }
}
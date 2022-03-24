package com.khailv

import android.app.Application
import com.khailv.flutter_callkit_incoming.SocketIoManager
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject

class App : Application() {
    override fun onCreate() {
        super.onCreate()

    }
    override fun onTerminate() {
        super.onTerminate()
    }

}
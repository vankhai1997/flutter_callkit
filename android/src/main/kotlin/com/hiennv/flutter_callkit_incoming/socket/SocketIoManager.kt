package com.hiennv.flutter_callkit_incoming.socket

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import com.google.firebase.messaging.FirebaseMessagingService
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import org.json.JSONObject


class SocketIoManager {
    companion object {
        val instance = SocketIoManager()
    }

    var socket: Socket? = null
    var token: String? = null

    fun connect(context: Context) {
        val pref: SharedPreferences =
            context.getSharedPreferences(
                PreKey.SHARE_PRE,
                FirebaseMessagingService.MODE_PRIVATE
            )
        token = pref.getString(PreKey.TOKEN, null)
        if (socket == null && token != null) {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            options.query = "token=$token"
            options.reconnection = true
            socket = IO.socket(Const.SOCKET_URL, options)
            socket?.connect()
        }
//        _listenEvent(context);
    }


    fun disConnect() {
        socket?.disconnect()
        socket = null
    }

    @SuppressLint("HardwareIds")
    fun emitCancel(
        roomId: String,
        receiverId: String,
        senderId: String,
        senderDeviceId: String,
        context: Context
    ) {

        val json = JSONObject()
        val deviceId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        );
        json.put("roomId", roomId)
        json.put("senderId", senderId)
        json.put("senderDeviceId", senderDeviceId)
        json.put("receiverId", receiverId)
        json.put("deviceId", deviceId)
        socket?.emit(Const.REJECT_CALL, json)
        disConnect()
    }
}
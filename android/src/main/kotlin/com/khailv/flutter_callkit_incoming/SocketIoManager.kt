package com.meeyland

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.messaging.FirebaseMessagingService
import com.meeyland.utis.Const
import com.meeyland.utis.PreKey
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import org.json.JSONObject


class SocketIoManager {
    companion object {
        val instance = SocketIoManager()
    }

    private var socket: Socket? = null
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
    }

    fun disConnect() {
        socket?.disconnect()
        socket = null
    }

    fun emitCancel(roomId: String) {
        val json = JSONObject()
        json.put("roomId", roomId)
        socket?.emit(Const.REJECT_CALL, json)
    }
}
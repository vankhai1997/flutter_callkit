package com.khailv.flutter_callkit_incoming

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import com.khailv.flutter_callkit_incoming.utis.Const
import com.khailv.flutter_callkit_incoming.utis.PreKey
import com.khailv.flutter_callkit_incoming.utis.SocketEventType
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
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

    fun _listenEvent(context: Context) {
        socket?.on(Const.CALL_EVENT, Emitter.Listener {
            it.let {
                val data = it.get(0)
                Log.d("CALLLLLLLL",data.toString());
                data.let { it1 ->
                    val call = Gson().fromJson(it1.toString(), SocketModel::class.java)
                    Log.d("CALLLLLLLL111111",call.type);

//                    Handler(Looper.getMainLooper()).post {
//                        Toast.makeText(context, call.type+"    "+it, Toast.LENGTH_SHORT).show();
//                    }
                    if (call.type == SocketEventType.REJECT_CALL_TO_RECEIVER ||
                        call.type == SocketEventType.DISCONNECT_CALL ||
                        call.type == SocketEventType.RECEIVED_CALL_TO_RECEIVER ||
                        call.type == SocketEventType.MISS_CALL ||
                        call.type == SocketEventType.STOP_CALL_TO_RECEIVER
                    ) {
                        val callkit = FlutterCallkitIncomingPlugin.getInstance();
                        callkit.endAllCalls();

                    }

                }

            }

        })
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
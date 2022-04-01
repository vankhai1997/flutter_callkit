package com.khailv.flutter_callkit_incoming

 class SocketModel(
    val senderId: String?,
    val roomId: String?,
    val senderName: String,
    val receiverId: String,
    val senderDeviceId: String,
    val type: String,
    val message: String,
    val time: String,
)
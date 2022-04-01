package com.khailv.flutter_callkit_incoming.utis

object Const {
        const val SOCKET_URL = "http://192.168.4.22:8082/"
//    const val SOCKET_URL = "http://192.168.4.49:8082/"

    const val REJECT_CALL = "rejectCall"
    const val CALL_EVENT = "call_event"
    const val receivedToOtherDevice = "receivedToOtherDevice"
    const val rejectToOtherDevice = "rejectToOtherDevice"
}
object SocketEventType {
    // bắn đén các device khác tắt notify cuộc gọi
     const val REJECT_CALL_TO_RECEIVER = "REJECT_CALL_TO_RECEIVER";

    // bắn đến user B người nhận bận
     const val REJECT_CALL_TO_SENDER = "REJECT_CALL_TO_SENDER";

    //bắn cho device khác của người nhận
     const val RECEIVED_CALL_TO_RECEIVER = "RECEIVED_CALL_TO_RECEIVER";

    //bắn cho device của người gọi
     const val RECEIVED_CALL_TO_SENDER = "RECEIVED_CALL_TO_SENDER";

    // gửi message báo hủy cuộc gọi đến các thằng khác trong cuộc gọi
     const val END_CALL = "END_CALL";

    //user B bị mất kết nối
     const val DISCONNECT_CALL = "DISCONNECT_CALL";

    //gửi cho device nhận
     const val STOP_CALL_TO_RECEIVER = "STOP_CALL_TO_RECEIVER";
     const val MISS_CALL = "MISS_CALL";

     const val RECEIVER_IS_BUSY = "RECEIVER_IS_BUSY";
     const val JOINED_CALL = "JOINED_CALL";
}

object PreKey {
    const val ID = "flutter.id_calling"
    const val TOKEN = "TOKEN"
    const val SHARE_PRE = "meeyteam_callkit_incoming"
}


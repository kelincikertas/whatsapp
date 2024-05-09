package com.message.whatsapp.model;

import lombok.Data;

@Data
public class Message {
    int id;
    int room_id;
    int user_id;
    int reply_id;
    int status;
    String message_type;
    String message;
}
package com.message.whatsapp.model;

import lombok.Data;

@Data
public class MessageData {
    int roomId;
    int userId;
    int replyId;
    String messageType;
    String message;
    String token;
}
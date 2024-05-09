package com.message.whatsapp.model;

import java.util.List;

import lombok.Data;

@Data
public class Room {
    Integer id;
    String name;
    String photo;
    String message;
    String messageType;

    // from DB
    Integer room_id;
    Integer user_id;
    Integer status;
    String type;
    String room_name;
    String room_photo;
    Message lastMessage;
    List<User> members;
}
package com.message.whatsapp.model;

import java.util.List;

import lombok.Data;

@Data
public class CreateRoomData {
    int userId;
    String roomType;
    String name;
    String token;
    List<String> phones;
}
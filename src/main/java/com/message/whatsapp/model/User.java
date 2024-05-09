package com.message.whatsapp.model;

import lombok.Data;

@Data
public class User {
    int id;
    String name;
    String phone;
    String photo;
    String token;
}
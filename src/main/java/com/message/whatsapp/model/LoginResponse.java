package com.message.whatsapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResponse extends ResultResponse{
    public User user;
}

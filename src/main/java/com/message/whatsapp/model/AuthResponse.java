package com.message.whatsapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthResponse extends BasicResponse{
    User user;
}

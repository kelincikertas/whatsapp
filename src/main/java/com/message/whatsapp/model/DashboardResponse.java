package com.message.whatsapp.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DashboardResponse extends ResultResponse{
    List<Room> rooms;
}

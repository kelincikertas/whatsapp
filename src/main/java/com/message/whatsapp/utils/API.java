package com.message.whatsapp.utils;

public class API {
    // from DB API
    final static String BASE_API = "http://api.kelincikertas.com/";
    final static String PATH_REGISTER = "waregis";
    final static String PATH_LOGIN = "walogin";
    final static String PATH_ROOMS = "warooms";
    final static String PATH_CREATE_ROOMS = "wacreaterooms";
    final static String PATH_ADD_MEMBER = "waaddmember";
    final static String PATH_SEND_MESSAGE = "wasendmessage";
    final static String PATH_GET_MESSAGE = "wagetmessage";
    
    public static String getRegisterUrl(){
        return BASE_API+PATH_REGISTER;
    }
    
    public static String getLoginUrl(){
        return BASE_API+PATH_LOGIN;
    }

    public static String getRoomListUrl(){
        return BASE_API+PATH_ROOMS;
    }

    public static String getCreateRoomUrl(){
        return BASE_API+PATH_CREATE_ROOMS;
    }

    public static String getAddMemberUrl(){
        return BASE_API+PATH_ADD_MEMBER;
    }

    public static String getSendMEssageUrl(){
        return BASE_API+PATH_SEND_MESSAGE;
    }

    public static String getRetrieveMessageUrl(){
        return BASE_API+PATH_GET_MESSAGE;
    }


}
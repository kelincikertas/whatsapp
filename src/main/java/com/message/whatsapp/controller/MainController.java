package com.message.whatsapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.message.whatsapp.model.AuthResponse;
import com.message.whatsapp.model.BasicResponse;
import com.message.whatsapp.model.CreateRoomData;
import com.message.whatsapp.model.DashboardResponse;
import com.message.whatsapp.model.HomeResponse;
import com.message.whatsapp.model.LoginResponse;
import com.message.whatsapp.model.MessageData;
import com.message.whatsapp.model.ResultResponse;
import com.message.whatsapp.model.Room;
import com.message.whatsapp.utils.API;
import com.message.whatsapp.utils.Const;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import reactor.core.publisher.Mono;

@RestController
public class MainController {
    
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String register(@RequestParam String phoneNumber) {
		// public Mono<BaseResponse<Set<String>>> register(@RequestParam String phoneNumber) {
		LoginResponse response = new LoginResponse();
		
		if(phoneNumber.equals("")){
			response.setStatusCode(Const.CODE_INVALID_DATA);
			response.setStatusMessage(Const.MESSAGE_INVALID_DATA);
		} else{
			AuthResponse authResponse;
			HttpResponse<AuthResponse> postRequest = Unirest.post(API.getRegisterUrl())
				.header("accept", "application/json")
				.field("phone", phoneNumber)
				.asObject(AuthResponse.class);

			authResponse = postRequest.getBody();
				
			if(authResponse.getCode()==Const.API_CODE_SUCCESS){
				response.setStatusCode(Const.CODE_SUCCESS);
				response.setStatusMessage(Const.MESSAGE_SUCCESS);
				response.user = authResponse.getUser();
			} else {
				response.setStatusCode(Const.CODE_INVALID_DATA);
				response.setStatusMessage(Const.MESSAGE_INVALID_DATA);
			}
		}
		
		return new Gson().toJson(response);
	}

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String login(@RequestParam String phoneNumber) {
		LoginResponse response = new LoginResponse();
		
		if(phoneNumber.equals("")){
			response.setStatusCode(Const.CODE_INVALID_DATA);
			response.setStatusMessage(Const.MESSAGE_INVALID_DATA);
		} else{
			AuthResponse authResponse;
			HttpResponse<AuthResponse> postRequest = Unirest.post(API.getLoginUrl())
				.header("accept", "application/json")
				.field("phone", phoneNumber)
				.asObject(AuthResponse.class);

			authResponse = postRequest.getBody();
				
			if(authResponse.getCode()==Const.API_CODE_SUCCESS){
				response.setStatusCode(Const.CODE_SUCCESS);
				response.setStatusMessage(Const.MESSAGE_SUCCESS);
				response.user = authResponse.getUser();
			} else {
				response.setStatusCode(Const.CODE_INVALID_DATA);
				response.setStatusMessage(Const.MESSAGE_INVALID_DATA);
			}
		}
		
		return new Gson().toJson(response);
	}

	@RequestMapping(value = "/roomList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getRooms(@RequestParam int userId, @RequestParam String token) {
		HomeResponse homeResponse;
		DashboardResponse dashboardResponse = new DashboardResponse();
		List<Room> rooms = new ArrayList<>();
		HttpResponse<HomeResponse> postRequest = Unirest.post(API.getRoomListUrl())
			.header("accept", "application/json")
			.field("userid", userId)
			.field("token", token)
			.asObject(HomeResponse.class);

		homeResponse = postRequest.getBody();
		
		for (Room room : homeResponse.rooms) {
			Room dataRoom = new Room();
			dataRoom.setId(room.getRoom_id());
			dataRoom.setName(room.getRoom_name());
			dataRoom.setPhoto(room.getRoom_photo());
			dataRoom.setMessage(room.getLastMessage().getMessage());
			dataRoom.setMessageType(room.getLastMessage().getMessage_type());
			rooms.add(dataRoom);
		}
		dashboardResponse.setStatusCode(Const.CODE_SUCCESS);
		dashboardResponse.setStatusMessage(Const.MESSAGE_SUCCESS);
		dashboardResponse.setRooms(rooms);;
		
		return new Gson().toJson(dashboardResponse);
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String sendMessage(@RequestBody MessageData messageData) {
		ResultResponse resultResponse = new ResultResponse();
		BasicResponse response;
		HttpResponse<BasicResponse> postRequest = Unirest.post(API.getSendMEssageUrl())
			.header("accept", "application/json")
			.field("userid", messageData.getUserId())
			.field("token", messageData.getToken())
			.field("roomid", String.valueOf(messageData.getRoomId()))
			.field("messagetype", messageData.getMessageType())
			.field("message", messageData.getMessage())
			.asObject(BasicResponse.class);

			response = postRequest.getBody();
		
		if(response.getCode()==Const.API_CODE_SUCCESS){
			resultResponse.setStatusCode(Const.CODE_SUCCESS);
			resultResponse.setStatusMessage(Const.MESSAGE_SUCCESS);
		} else {
			resultResponse.setStatusCode(Const.CODE_INVALID_DATA);
			resultResponse.setStatusMessage(Const.MESSAGE_INVALID_DATA);
		}

		return new Gson().toJson(resultResponse);
	}

	@RequestMapping(value = "/createRoom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String createRoom(@RequestBody CreateRoomData roomData) {
		ResultResponse resultResponse = new ResultResponse();
		BasicResponse response;
		String errorMessage = "";
		int errorNumber = 0;
		for (String phone : roomData.getPhones()) {
			HttpResponse<BasicResponse> postRequest = Unirest.post(API.getCreateRoomUrl())
			.header("accept", "application/json")
			.field("userid", roomData.getUserId())
			.field("token", roomData.getToken())
			.asObject(BasicResponse.class);
			
			response = postRequest.getBody();

			if(response.getCode()==Const.API_CODE_FAILED) {
				errorNumber++;
				if(errorMessage.isEmpty()) errorMessage+=phone;
				else errorMessage+=", "+phone;
			}
		}
		
		if(errorMessage.isEmpty()){
			resultResponse.setStatusCode(Const.CODE_SUCCESS);
			resultResponse.setStatusMessage(Const.MESSAGE_SUCCESS);
		} else {
			if(errorNumber==roomData.getPhones().size()){
				resultResponse.setStatusCode(Const.CODE_INVALID_DATA);
				resultResponse.setStatusMessage(Const.MESSAGE_INVALID_DATA);
			}else {
				resultResponse.setStatusCode(Const.CODE_INVALID_PARTIAL_DATA);
				resultResponse.setStatusMessage(errorMessage+" are invalid");
			}
		}
		
		
		return new Gson().toJson(resultResponse);
	}

	@RequestMapping(value = "/addMember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addMember(@RequestParam int userId, @RequestParam String token, @RequestParam String phoneNumber) {
		BasicResponse response;
		ResultResponse resultResponse = new ResultResponse();
		
		HttpResponse<BasicResponse> postRequest = Unirest.post(API.getAddMemberUrl())
			.header("accept", "application/json")
			.field("userid", userId)
			.field("token", token)
			.field("phone", phoneNumber)
			.asObject(BasicResponse.class);

			response = postRequest.getBody();
		
			if(response.getCode()==Const.API_CODE_SUCCESS){
				resultResponse.setStatusCode(Const.CODE_SUCCESS);
				resultResponse.setStatusMessage(Const.MESSAGE_SUCCESS);
			} else {
				resultResponse.setStatusCode(Const.CODE_INVALID_DATA);
				resultResponse.setStatusMessage(Const.MESSAGE_INVALID_DATA);
			}
		
		return new Gson().toJson(resultResponse);
	}
}

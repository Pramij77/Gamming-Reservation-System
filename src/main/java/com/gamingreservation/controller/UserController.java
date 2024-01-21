package com.gamingreservation.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamingreservation.dto.UserDTO;
import com.gamingreservation.model.UserModel;
import com.gamingreservation.service.UserModelService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserModelService userModelService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/signUp")
	public ResponseEntity<UserDTO> signUp(@RequestBody UserModel userModel){
		UserModel user = userModelService.saveUser(userModel);
		UserDTO userDTO = mapper.map(user, UserDTO.class);
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
		
	}
}

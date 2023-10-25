package com.diogo.backPraticaFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.diogo.backPraticaFinal.dtos.user.ChangePasswordRequestDTO;
import com.diogo.backPraticaFinal.dtos.user.UserResponseDTO;
import com.diogo.backPraticaFinal.models.User;
import com.diogo.backPraticaFinal.services.TokenService;
import com.diogo.backPraticaFinal.services.UserService;

@RestController
public class UserController {

	//############################################################################################
	
	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;
	
	//############################################################################################

	@GetMapping("myApp/user")
	public ResponseEntity<UserResponseDTO> getUserByToken(@RequestHeader("Authorization") String authorizationHeader){
		String token = tokenService.extractTokenFromRequest(authorizationHeader);

		User user = tokenService.getUserByToken(token);

		return new ResponseEntity<UserResponseDTO>(new UserResponseDTO(user, token),HttpStatus.OK);
	}

	@PostMapping("myApp/user/edit")
	public ResponseEntity<Void> editUser(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody  User userToEdit ){

		//get user by token
		String token = tokenService.extractTokenFromRequest(authorizationHeader);
		User user = tokenService.getUserByToken(token);

		userService.editUser(token, user, userToEdit);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/myApp/user/change-password")
	public ResponseEntity<Void> changePassword(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody ChangePasswordRequestDTO changePasswordRequest){

		String token = tokenService.extractTokenFromRequest(authorizationHeader);
		User user = tokenService.getUserByToken(token);

		userService.changePassword(token, user, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());

		return new ResponseEntity<Void>(HttpStatus.OK);

	}
}
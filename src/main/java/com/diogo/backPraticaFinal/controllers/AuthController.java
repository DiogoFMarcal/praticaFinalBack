package com.diogo.backPraticaFinal.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.diogo.backPraticaFinal.dtos.user.LoginRequestDTO;
import com.diogo.backPraticaFinal.dtos.user.UserResponseDTO;
import com.diogo.backPraticaFinal.models.User;
import com.diogo.backPraticaFinal.services.TokenService;
import com.diogo.backPraticaFinal.services.UserService;

@RestController
public class AuthController {

	//############################################################################################
	
	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	//############################################################################################
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Valid User userRegister) {
		User user = userService.createUser(userRegister);

		return new ResponseEntity<User>( user, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponseDTO> login( @RequestBody @Valid LoginRequestDTO loginUser){

		User user = userService.login(loginUser);
		String userToken = tokenService.createSession(user);

		return new ResponseEntity<UserResponseDTO>( new UserResponseDTO(user, userToken), HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader){
		
		String token = tokenService.extractTokenFromRequest(authorizationHeader);
		tokenService.removeSession(token);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
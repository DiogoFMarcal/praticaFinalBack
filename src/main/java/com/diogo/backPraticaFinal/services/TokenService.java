package com.diogo.backPraticaFinal.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.diogo.backPraticaFinal.models.Session;
import com.diogo.backPraticaFinal.models.User;

@Service
public class TokenService {

	//10 minutes
	private  final long SESSION_TIMEOUT = 600000;
	private Map<String, Session> activeSessions = new HashMap<>();	

	public  String createSession(User user) {

		for (Map.Entry<String, Session> entry : activeSessions.entrySet()) {
			Session session = entry.getValue();

			if(session.getUser().getId() == user.getId()) {
				return entry.getKey();
			}
		}

		String token = generateUUIDFromEmail(user.getEmail()).toString();

		Session newSession = new Session(user, System.currentTimeMillis() );
		activeSessions.put(token, newSession );
		return token;
	}

	private UUID generateUUIDFromEmail(String email) {
		//generate uuid from email because it is unique
		try {

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] emailBytes = email.getBytes();
			byte[] digest = md.digest(emailBytes);
			return UUID.nameUUIDFromBytes(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public  boolean isValidSession(String token) {

		//  checks if the given sessionId is in active sessions
		if(activeSessions.get(token) == null) {
			return false;
		}

		//check if the session for the given token is valid
		Long sessionStartTime = activeSessions.get(token).getSessionStartTime();
		long currentTime = System.currentTimeMillis();
		boolean tokenIsValid = (currentTime - sessionStartTime) < SESSION_TIMEOUT;
		if(!tokenIsValid) {
			removeSession(token);
		}
		
		//do the same for all the sessions
		for (Map.Entry<String, Session> entry : activeSessions.entrySet()) {
			sessionStartTime = entry.getValue().getSessionStartTime();
			currentTime = System.currentTimeMillis();
			tokenIsValid = (currentTime - sessionStartTime) < SESSION_TIMEOUT;
			if(!tokenIsValid) {
				removeSession(entry.getKey());
			}
		}

		return tokenIsValid;
	}

	public String extractTokenFromRequest(String authorization) {
		if (authorization != null && authorization.startsWith("Bearer ")) {
			//extract the token from the request header
			String token = authorization.substring(7);

			if(!isValidSession(token)) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			};
			return token;
		}
		return "";
	}

	public  void removeSession(String sessionId) {
		//ler o token do cabeçalho do pedido e depois remover a sessao
		activeSessions.remove(sessionId);
	}

	public User getUserByToken(String token){
		return activeSessions.get(token).getUser();
	}

	public Map<String, Session> getActiveSessions() {
		return activeSessions;
	}

	public void editUserInActiveSessionsByToken(String token, User editedUser) {
		activeSessions.get(token).setUser(editedUser);
	};
}
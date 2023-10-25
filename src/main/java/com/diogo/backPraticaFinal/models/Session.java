package com.diogo.backPraticaFinal.models;

public class Session {

	private long sessionStartTime;
	
	private User user;
	
	public Session( User user, long sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
		this.user = user;
	}

	public Session() {}
	
	public long getSessionStartTime() {
		return sessionStartTime;
	}

	public void setSessionStartTime(long sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

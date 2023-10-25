package com.diogo.backPraticaFinal.dtos.user;

import com.diogo.backPraticaFinal.models.User;

public class UserResponseDTO {

	//############################################################################################
	
	//same as a user response but without the password
	
	private Integer id;

	private String name;

	private String surnames;

	private String email;

	private String country;

	private String city;

	private String address;

	private String iban;
	
	private String token;
	
	//#################################################################################################################

	public UserResponseDTO(Integer id, String name, String surnames, String email, String country, String city,
			String address, String iban, String token) {
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		this.email = email;
		this.country = country;
		this.city = city;
		this.address = address;
		this.iban = iban;
		this.token = token;
	}
	
	public UserResponseDTO(User user, String token) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.surnames = user.getSurnames();
		this.email = user.getEmail();
		this.country = user.getCountry();
		this.city = user.getCity();
		this.address = user.getAddress();
		this.iban = user.getIban();
		this.token = token;
	}

	public UserResponseDTO() {}

	//#################################################################################################################
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	
}
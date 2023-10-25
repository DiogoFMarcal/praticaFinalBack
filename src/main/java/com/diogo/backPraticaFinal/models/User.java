package com.diogo.backPraticaFinal.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
//rename the table to make sure there are not conflicts with any users table in db
@Table(name = "app_users")
public class User {

	//##########################################################################################
	//atributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "surnames")
	private String surnames;

	
	@NotNull
	@Column(name = "password" )
	private String password;

	@NotBlank
	@Column(name = "email")
	private String email;

	@Column(name = "country")
	private String country;

	@Column(name = "city")
	private String city;

	@Column(name = "address")
	private String address;

	//os rimeiros dois caracteres devem ser letras e os restantes 22 devem ser numeros
	@Column(name = "iban")
	private String iban;

	//##########################################################################################
	//constructors

	public User(String name, String surnames, String password, String email, String country, String city,
			String address, String iban) {
		this.name = name;
		this.surnames = surnames;
		this.password = password;
		this.email = email;
		this.country = country;
		this.city = city;
		this.address = address;
		this.iban = iban;
	}

	public User(Integer id, String name, String surnames, String password, String email, String country, String city,
			String adress, String iban) {
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		this.password = password;
		this.email = email;
		this.country = country;
		this.city = city;
		this.address = adress;
		this.iban = iban;
	}

	public User() {}


	//##########################################################################################
	//getters e setters

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	//##########################################################################################
	//other methods

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surnames=" + surnames + ", password=" + password + ", email="
				+ email + ", country=" + country + ", city=" + city + ", address=" + address + ", iban=" + iban + "]";
	}

	

}

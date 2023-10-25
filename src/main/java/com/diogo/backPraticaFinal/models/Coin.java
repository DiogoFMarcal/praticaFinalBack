package com.diogo.backPraticaFinal.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coin")
public class Coin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	//one unit of this coin is equivalent to x euros
	@Column(name = "quoteForEuro")
	private double quoteForEuro;

	//######################################################################################

	public Coin(String name, double quoteForEuro) {
		this.name = name;
		this.quoteForEuro = quoteForEuro;
	}

	public Coin() {	}

	//######################################################################################

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuoteForEuro() {
		return quoteForEuro;
	}

	public void setQuoteForEuro(double quoteForEuro) {
		this.quoteForEuro = quoteForEuro;
	}

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
		Coin other = (Coin) obj;
		return id == other.id;
	}
}

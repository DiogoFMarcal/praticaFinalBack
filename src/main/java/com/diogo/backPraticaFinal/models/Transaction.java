package com.diogo.backPraticaFinal.models;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "date")
	private Date date;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "amountInEuros")
	private double amountInEuros;

	@Column(name = "operationType")
	private OperationType operationType;

	@ManyToOne()
	@JoinColumn(name = "source_coin_id", referencedColumnName = "id")
	private Coin sourceCoin;

	@ManyToOne()
	@JoinColumn(name = "destination_coin_id", referencedColumnName = "id")
	private Coin destinationCoin;

	//#########################################################################################################

	public Transaction( User user, double amountInEuros, OperationType operationType, Coin sourceCoin,
			Coin destinationCoin) {
		//set the curr date and time
		this.date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		this.user = user;
		this.amountInEuros = amountInEuros;
		this.operationType = operationType;
		this.sourceCoin = sourceCoin;
		this.destinationCoin = destinationCoin;
	}

	public Transaction() {}

	//#########################################################################################################

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getAmountInEuros() {
		return amountInEuros;
	}

	public void setAmountInEuros(double amountInEuros) {
		this.amountInEuros = amountInEuros;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Coin getSourceCoin() {
		return sourceCoin;
	}

	public void setSourceCoin(Coin sourceCoin) {
		this.sourceCoin = sourceCoin;
	}

	public Coin getDestinationCoin() {
		return destinationCoin;
	}

	public void setDestinationCoin(Coin destinationCoin) {
		this.destinationCoin = destinationCoin;
	}

	//#########################################################################################################

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
		Transaction other = (Transaction) obj;
		return Objects.equals(id, other.id);
	}






}

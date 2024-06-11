package me.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;

@Entity
@NamedQueries({
		@NamedQuery(name = Phone.GET_ALL_FOR_CUSTOMER, query = "SELECT p FROM Phone p WHERE p.customer.id = :id") })
public class Phone {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
	@SequenceGenerator(name = "phone_seq", sequenceName = "phone_sequence", allocationSize = 1)

	public Long id;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "customerID")
	public Customer customer;

	public String number;
	public static final String GET_ALL_FOR_CUSTOMER = "getAllPhonesForCustomer";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String broj) {
		this.number = broj;
	}

}

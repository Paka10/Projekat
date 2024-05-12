package me.fit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;


@Entity
@NamedQueries({
    @NamedQuery(name = Phone.GET_ALL_FOR_CUSTOMER, query = "SELECT p FROM Phone p WHERE p.users.id = :id")
})
public class Phone {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
	public Long id;
	
	public Customer customer;
	public String broj;
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

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

}

package me.fit.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
	private Long rentalID;
	private String rentalDate;
	private String returnDate;
	private boolean returned;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public Long getRentalId() {
		return rentalID;
	}

	public void setRentalId(Long rentalId) {
		this.rentalID = rentalId;
	}

	public String getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(String rentalDate) {
		this.rentalDate = rentalDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, rentalDate, rentalID, returnDate, returned);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rental other = (Rental) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(rentalDate, other.rentalDate)
				&& Objects.equals(rentalID, other.rentalID) && Objects.equals(returnDate, other.returnDate)
				&& returned == other.returned;
	}

}

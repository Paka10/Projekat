package me.fit.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PreRemove;
import jakarta.persistence.SequenceGenerator;
import jakarta.transaction.Transactional;

@Entity
@NamedQueries({ @NamedQuery(name = Rental.GET_ALL_RENTALS, query = "Select r from Rental r") })
public class Rental {
	public static final String GET_ALL_RENTALS = "getAllRentals";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
	@SequenceGenerator(name = "rental_seq", sequenceName = "rental_sequence", allocationSize = 1)
	private Long rentalID;
	@JsonIgnore
	private Date rentalDate;
	private Date returnDate;
	private boolean returned;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerID")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicleID")
	private Vehicle vehicle;

	@JsonIgnore
	private double priceOfRental;
	
	@PreRemove
    private void preRemove() {
        this.customer = null;
        this.vehicle = null;
    }

	public double getPriceOfRental() {
		return priceOfRental;
	}

	public void setPriceOfRental(double priceOfRental) {
		this.priceOfRental = priceOfRental;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
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

	public Long getRentalID() {
		return rentalID;
	}

	public void setRentalID(Long rentalID) {
		this.rentalID = rentalID;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public void setRentalId(Long rentalId) {
		this.rentalID = rentalId;
	}

	public Date getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Date rentalDate) {
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

	@Transactional
	public void calculateRentalprice() {
		LocalDate startDate = rentalDate.toLocalDate();
		LocalDate endDate = returnDate.toLocalDate();

		Long days = ChronoUnit.DAYS.between(startDate, endDate);
		this.priceOfRental = vehicle.getPrice() * days;

	}

}

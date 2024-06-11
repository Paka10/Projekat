package me.fit.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.RentalStatus;
import me.fit.exception.CustomerException;
import me.fit.exception.RentalException;
import me.fit.model.Customer;
import me.fit.model.Rental;
import me.fit.model.Vehicle;

@Dependent
public class RentalService {

	@Inject
	private EntityManager em;

	@Transactional
	public Rental createRental(Rental r, Long customerID, Long vehicleID) throws RentalException {
		Customer customer = em.find(Customer.class, customerID);
		if (customer == null) {
			throw new RentalException("Customer with this id does not exist");
		}
		Vehicle vehicle = em.find(Vehicle.class, vehicleID);
		if (vehicle == null) {
			throw new RentalException("Vehicle with this id does not exist");
		}

		if (customerID == null || vehicleID == null) {
			throw new RentalException("ID's must be provided.");
		}

//		customer = em.find(Customer.class, customerID);
//		vehicle = em.find(Vehicle.class, vehicleID);
		r.setCustomer(customer);
		r.setVehicle(vehicle);
		r.setRentalDate(Date.valueOf(LocalDate.now()));
		r.calculateRentalprice();

		List<Rental> rentals = getAllRentals();

		if (rentals.contains(r)) {
			throw new RentalException(RentalStatus.EXISTS.getLabel());
		}

		return em.merge(r);
	}

	@Transactional
	public List<Rental> getAllRentals() {
		List<Rental> rentals = em.createNamedQuery(Rental.GET_ALL_RENTALS, Rental.class).getResultList();
		return rentals;
	}

	@Transactional
	public void deleteRentalByID(Long rentalID) throws RentalException {
		Rental rental = em.find(Rental.class, rentalID);
		if (rental == null) {
			throw new RentalException("Rental with the ID: " + rentalID + " does not exist.");
		}
		em.remove(rental);
	}

}

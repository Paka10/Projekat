package me.fit.service;

import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.RentalStatus;
import me.fit.exception.RentalException;
import me.fit.model.Rental;

@Dependent
public class RentalService {

	@Inject
	private EntityManager em;

	@Transactional
	public Rental createRental(Rental r) throws RentalException {
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

}

package me.fit.service;

import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.VehicleStatus;
import me.fit.exception.VehicleException;
import me.fit.model.Vehicle;

@Dependent
public class VehicleService {

	@Inject
	private EntityManager em;

	@Transactional
	public Vehicle createVehicle(Vehicle v) throws VehicleException {
		List<Vehicle> vehicles = getAllVehicles();
		if (vehicles.contains(v)) {
			throw new VehicleException(VehicleStatus.EXISTS.getLabel());	
		}
		return em.merge(v);
	}

	@Transactional
	public List<Vehicle> getAllVehicles() {
		List<Vehicle> vehicles = em.createNamedQuery(Vehicle.GET_ALL_VEHICLES, Vehicle.class).getResultList();
		return vehicles;
	}
}

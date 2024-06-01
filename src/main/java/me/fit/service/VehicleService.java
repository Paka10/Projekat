package me.fit.service;

import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.VehicleStatus;
import me.fit.exception.VehicleException;
import me.fit.model.Brand;
import me.fit.model.Vehicle;

@Dependent
public class VehicleService {

	@Inject
	private EntityManager em;

	@Transactional
	public Vehicle createVehicle(Vehicle v, Long brandID) throws VehicleException {
		Brand brand = getBrandById(brandID);
	    if (brand == null) {
	        throw new VehicleException("Brand with ID " + brandID + " does not exist.");
	    }
	    v.setBrand(brand);
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
	
	@Transactional
	public void deleteVehicleByID(Long vehicleID) throws VehicleException {
		
		Vehicle vehicle = em.find(Vehicle.class, vehicleID);
		if (vehicle == null) {
			throw new VehicleException("Vehicle with the ID: " + vehicleID + " does not exist.");
		}
		em.remove(vehicle);
		
	}
	
	@Transactional
	public Brand getBrandById(Long brandId) {
	    return em.find(Brand.class, brandId);
	}
}

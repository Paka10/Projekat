package me.fit.service;

import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.BrandStatus;
import me.fit.exception.BrandException;
import me.fit.exception.VehicleException;
import me.fit.model.Brand;
import me.fit.model.Rental;
import me.fit.model.Vehicle;

@Dependent
public class BrandService {

	@Inject
	private EntityManager em;

	@Transactional
	public Brand createBrand(Brand b) throws BrandException {
		List<Brand> brands = getAllBrands();
		if (brands.contains(b)) {
			throw new BrandException(BrandStatus.EXISTS.getLabel());
		}
		return em.merge(b);
	}

	@Transactional
	public List<Brand> getAllBrands() {
		List<Brand> brands = em.createNamedQuery(Brand.GET_ALL_BRANDS, Brand.class).getResultList();
		return brands;
	}

	@Transactional
	public void deleteBrandByID(Long brandID) throws BrandException {

		Brand brand = em.find(Brand.class, brandID);
		if (brand == null) {
			throw new BrandException("Brand with the ID: " + brandID + " does not exist.");
		}

//		for (Vehicle vehicle : brand.getVehicles()) {
//
//			for (Rental rentals : vehicle.getRentals()) {
//				em.remove(rentals);
//			}
//			vehicle.getRentals().clear();
//			em.remove(vehicle);
//		}
//		brand.getVehicles().clear();
//		em.remove(brand);

//		for (Vehicle vehicle : brand.getVehicles()) {
//			for (Rental rental : vehicle.getRentals()) {
//				em.remove(rental);
//			}
//			vehicle.getRentals().clear();
//			em.remove(vehicle);
//		}
//		brand.getVehicles().clear();
		em.remove(brand);

	}

	@Transactional
	public Brand updateBrandName(Long brandID, String newName) throws BrandException {

		Brand brand = em.find(Brand.class, brandID);
		if (brand == null) {
			throw new BrandException("Brand with the ID:" + brandID + " does not exist.");
		}
		brand.setBrandName(newName);
		return em.merge(brand);

	}

}

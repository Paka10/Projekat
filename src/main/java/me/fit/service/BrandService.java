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
	public void deleteBrandByID(Long brandID) throws VehicleException {

		Brand brand = em.find(Brand.class, brandID);
		if (brand == null) {
			throw new VehicleException("Brand with the ID: " + brandID + " does not exist.");
		}
		em.remove(brand);

	}

}

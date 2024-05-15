package me.fit.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.BrandStatus;
import me.fit.exception.BrandException;
import me.fit.model.Brand;

@ApplicationScoped
@Transactional
public class BrandService {

	@Inject
	private EntityManager em;

	public Brand createBrand(Brand b) throws BrandException {
		List<Brand> brands = getAllBrands();
		if (brands.contains(b)) {
			throw new BrandException(BrandStatus.EXISTS.getLabel());
		}
		return em.merge(b);
	}

	public List<Brand> getAllBrands() {
		List<Brand> brands = em.createNamedQuery(Brand.GET_ALL_BRANDS, Brand.class).getResultList();
		return brands;
	}
}

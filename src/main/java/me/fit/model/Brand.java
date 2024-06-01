package me.fit.model;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = Brand.GET_ALL_BRANDS, query = "SELECT b FROM Brand b") })
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq")
	@SequenceGenerator(name = "brand_seq", sequenceName = "brand_sequence", allocationSize = 1)
	private Long brandID;
	private String brandName;

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "brandID")
	@JsonIgnore
	private Set<Vehicle> vehicles;
	
	
	public static final String GET_ALL_BRANDS = "getAllBrands";

	@Override
	public int hashCode() {
		return Objects.hash(brandID, brandName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Brand other = (Brand) obj;
		return Objects.equals(brandID, other.brandID) && Objects.equals(brandName, other.brandName);
	}

	@Override
	public String toString() {
		return "Brand [brandID=" + brandID + ", brandName=" + brandName + "]";
	}

	public Long getBrandID() {
		return brandID;
	}

	public void setBrandID(Long brandID) {
		this.brandID = brandID;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}

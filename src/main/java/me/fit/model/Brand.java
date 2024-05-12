package me.fit.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
	private Long brandID;

	private String brandName;

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

package me.fit.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = Vehicle.GET_ALL_VEHICLES, query = "SELECT v FROM Vehicle v WHERE v.id = :id")
})
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq")
	private Long id;
	private String model;
	private String year;
	private Double price;
	
	
	@ManyToOne
	@JoinColumn(name= "brandID")
	private Brand brand;
	
	
	public static final String GET_ALL_VEHICLES = "getAllVehicles";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", model=" + model + ", year=" + year + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, model, price, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(id, other.id) && Objects.equals(model, other.model) && Objects.equals(price, other.price)
				&& Objects.equals(year, other.year);
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}

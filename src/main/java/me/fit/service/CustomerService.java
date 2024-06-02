package me.fit.service;

import java.util.HashSet;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.CustomerStatus;
import me.fit.exception.CustomerException;
import me.fit.exception.VehicleException;
import me.fit.model.Customer;
import me.fit.model.IPLog;
import me.fit.model.Phone;
import me.fit.model.Vehicle;

@Dependent
public class CustomerService {

	@Inject
	private EntityManager em;

	@Transactional
	public List<Phone> getAllForCustomers(Customer c) {
		return em.createNamedQuery(Phone.GET_ALL_FOR_CUSTOMER, Phone.class).setParameter("id", c.getId())
				.getResultList();
	}

	@Transactional
	public Customer createCustomer(Customer c, IPLog ipLog) throws CustomerException {
		List<Customer> customers = getAllCustomers();
		if (customers.contains(c)) {
			throw new CustomerException(CustomerStatus.EXISTS.getLabel());
		}
		c.setIpLog(ipLog);
		return em.merge(c);
	}

	@Transactional
	public List<Customer> getCustomersByName(String name) {
		List<Customer> customers = em.createNamedQuery(Customer.GET_CUSTOMER_BY_NAME, Customer.class)
				.setParameter("name", name).getResultList();
		for (Customer customer : customers) {
			List<Phone> phoneNumbers = getAllForCustomers(customer);
			customer.setPhoneNumbers(new HashSet<>(phoneNumbers));
		}
		return customers;
	}

	@Transactional
	public List<Customer> getAllCustomers() {
		List<Customer> customers = em.createNamedQuery(Customer.GET_ALL_CUSTOMERS, Customer.class).getResultList();

		for (Customer customer : customers) {
			List<Phone> phoneNumbers = getAllForCustomers(customer);
			customer.setPhoneNumbers(new HashSet<>(phoneNumbers));

		}
		return customers;
	}

	@Transactional
	public void deleteCustomerByID(Long customerID) throws VehicleException {

		Customer customer = em.find(Customer.class, customerID);
		if (customer == null) {
			throw new VehicleException("Vehicle with the ID: " + customerID + " does not exist.");
		}
		em.remove(customer);

	}

}

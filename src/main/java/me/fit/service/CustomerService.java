package me.fit.service;

import java.util.HashSet;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.enums.CustomerStatus;
import me.fit.exception.CustomerException;
import me.fit.model.Customer;
import me.fit.model.Phone;

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
	public Customer createCustomer(Customer c) throws CustomerException {
		List<Customer> customers = getAllCustomers();
		if (customers.contains(c)) {
			throw new CustomerException(CustomerStatus.EXISTS.getLabel());
		}
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

}
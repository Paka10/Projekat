package me.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import me.fit.exception.CustomerException;
import me.fit.model.Customer;
import me.fit.service.CustomerService;

@Path("/api/customer")
public class CustomerRest {

	@Inject
	private CustomerService customerService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createCustomer")
	@Operation(summary = "Web service which creates a new customer.", description = "Customer must be uniqe")
	public Response createCustomer(Customer customer) {
		Customer c = null;
		try {
			c = customerService.createCustomer(c);
		} catch (CustomerException e) {
			return Response.status(Status.CONFLICT).entity(c).build();
		}
		return Response.ok().entity(c).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCustomers")
	public Response getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return Response.ok().entity(customers).build();
	}

}

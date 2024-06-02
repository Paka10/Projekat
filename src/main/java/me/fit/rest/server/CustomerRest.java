package me.fit.rest.server;

import java.util.Date;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import me.fit.exception.CustomerException;
import me.fit.exception.VehicleException;
import me.fit.model.Customer;
import me.fit.model.IPLog;
import me.fit.rest.client.IPClient;
import me.fit.service.CustomerService;

@Path("/api/customer")
public class CustomerRest {

	@Inject
	private CustomerService customerService;

	@Inject
	@RestClient
	private IPClient ipClient;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createCustomer")
	@Operation(summary = "Web service which creates a new customer.", description = "Customer must be unique")
	public Response createCustomer(Customer customer) {

		if (customer == null) {

			return Response.status(Status.BAD_REQUEST).entity("Customer object is null").build();
		}
		try {
			IPLog ipLog = ipClient.getIp();
			ipLog.setCreatedDate(new Date());
			Customer createdCustomer = customerService.createCustomer(customer, ipLog);
			return Response.ok().entity(createdCustomer).build();
		} catch (CustomerException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCustomers")
	public Response getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return Response.ok().entity(customers).build();
	}

	@DELETE
	@Path("/deleteCustomer/{customerID}")
	@Operation(summary = "Delete customer based on ID", description = "Removes customer based on his ID")
	public Response deleteCustomer(@PathParam("customerID") Long customerID) {
		try {
			customerService.deleteCustomerByID(customerID);
			return Response.status(Status.OK).build();
		} catch (VehicleException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}

	}

}

package me.fit.rest.server;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import me.fit.exception.CustomerException;
import me.fit.exception.VehicleException;
import me.fit.model.Country;
import me.fit.model.Customer;
import me.fit.model.IPLog;
import me.fit.model.Phone;
import me.fit.multipart.MultipartRequest;
import me.fit.rest.client.IPClient;
import me.fit.service.CountryService;
import me.fit.service.CustomerService;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import me.fit.multipart.MultipartResource.UploadSchema;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/api/customer")
public class CustomerRest {

	@Inject
	private CustomerService customerService;

	@Inject
	@RestClient
	private IPClient ipClient;
	
	@Inject
	private CountryService countryService;

//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Path("/createCustomer")
//	@Operation(summary = "Web service which creates a new customer.", description = "Customer must be unique")
//	public Response createCustomer(Customer customer) {
//
//		if (customer == null) {
//
//			return Response.status(Status.BAD_REQUEST).entity("Customer object is null").build();
//		}
//		try {
//			IPLog ipLog = ipClient.getIp();
//			ipLog.setCreatedDate(new Date());
//			Customer createdCustomer = customerService.createCustomer(customer, ipLog);
//			return Response.ok().entity(createdCustomer).build();
//		} catch (CustomerException e) {
//			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
//		}
//	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/createCustomer")
	@Operation(summary = "Web service that creates a new Customer.", description = "Customer should be unique.")
	public Response createCustomer(MultipartRequest request) {
		Customer c = new Customer();
		c.setName(request.getName());
		c.setLastName(request.getLastName());
		c.setEmail(request.getEmail());
		FileUpload fileUpload = request.getFile();

		if (fileUpload == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("File is missing").build();
		}
		
		//telefoni u objekte
		Set<Phone> phoneNumbers = new HashSet<>();
		for (String number : request.getPhoneNumbers()) {
			Phone p = new Phone();
			p.setNumber(number);
			phoneNumbers.add(p);
		}
		c.setPhoneNumbers(phoneNumbers);
		
		//zemlja
		Country country = countryService.getCountryByName(request.getCountryName());
		if (country == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid country").build();
		}
		c.setCountry(country);
		try {

			byte[] image = Files.readAllBytes(fileUpload.uploadedFile().toAbsolutePath());
			IPLog ipLog = ipClient.getIp();
			ipLog.setCreatedDate(new Date());
			c = customerService.createCustomer(c, ipLog, image);
			return Response.status(Status.CREATED).entity(c).build();
		} catch (CustomerException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		} catch (IOException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("File upload failed").build();
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

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateCustomerEmail/{customerID}/{newEmail}")
	public Response updateCustomerEmail(@PathParam("customerID") Long customerID,
			@PathParam("newEmail") String newEmail) {
		try {
			Customer updateCustomer = customerService.updateCustomerEmail(customerID, newEmail);
			return Response.ok().entity(updateCustomer).build();
		} catch (CustomerException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}

	}

}

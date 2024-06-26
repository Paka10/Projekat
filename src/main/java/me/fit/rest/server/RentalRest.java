package me.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

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
import me.fit.exception.RentalException;
import me.fit.model.Rental;
import me.fit.service.RentalService;

@Path("/api/rental")
public class RentalRest {

	@Inject
	private RentalService rentalService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createRental/{customerID}/{vehicleID}")
	@Operation(summary = "Web service which creates a Rental.", description = "Rental must be unique.")
	public Response createRental(Rental rental, @PathParam("customerID") Long customerID,
			@PathParam("vehicleID") Long vehicleID) {
		Rental r = null;
		try {
			r = rentalService.createRental(rental, customerID, vehicleID);
			return Response.ok().entity(r).build();
		} catch (RentalException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllRentals")
	public Response getAllRentals() {
		List<Rental> rentals = rentalService.getAllRentals();
		return Response.ok().entity(rentals).build();
	}

	@DELETE
	@Path("/deleteRental/{rentalID}")
	@Operation(summary = "Delete rental by ID", description = "Removes a rental deal using the provided ID.")
	public Response deleteRental(@PathParam("rentalID") Long rentalID) {

		try {
			rentalService.deleteRentalByID(rentalID);
			return Response.ok().build();
		} catch (RentalException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}

	}

}

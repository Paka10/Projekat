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
import me.fit.exception.VehicleException;
import me.fit.model.Vehicle;
import me.fit.service.VehicleService;

@Path("/api/vehicle")
public class VehicleRest {

	@Inject
	private VehicleService vehicleService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createVehicle")
	@Operation(summary = "Web service which creates a new object of Vehicle.", description = "Vehicle must be unique.")
	public Response createVehicle(Vehicle vehicle) {
		Vehicle v = null;
		try {
			v = vehicleService.createVehicle(vehicle);
		} catch (VehicleException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok().entity(v).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllVehicles")
	public Response getAllBooks() {
		List<Vehicle> vehicles = vehicleService.getAllVehicles();
		return Response.ok().entity(vehicles).build();
	}

}

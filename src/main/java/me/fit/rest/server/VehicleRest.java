package me.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

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
import me.fit.exception.VehicleException;
import me.fit.model.Vehicle;
import me.fit.service.VehicleService;

@Path("/api/vehicle")
public class VehicleRest {

	@Inject
	private VehicleService vehicleService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createVehicle/{brandID}")
	@Operation(summary = "Web service which creates a new object of Vehicle.", description = "Vehicle must be unique.")
	public Response createVehicle(Vehicle vehicle, @PathParam("brandID") Long brandID) {
		Vehicle v = null;
		try {
			v = vehicleService.createVehicle(vehicle, brandID);
		} catch (VehicleException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok().entity(v).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllVehicles")
	public Response getAllVehicles() {
		List<Vehicle> vehicles = vehicleService.getAllVehicles();
		return Response.ok().entity(vehicles).build();
	}

	@DELETE
	@Path("/deleteVehicle/{vehicleID}")
	@Operation(summary = "Delete vehicle by ID", description = "Removes a vehicle using the provided ID.")
	public Response deleteVehicle(@PathParam("vehicleID") Long vehicleID) {
		try {
			vehicleService.deleteVehicleByID(vehicleID);
			return Response.status(Status.OK).build();
		} catch (VehicleException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateVehiclePrice/{vehicleID}/{newPrice}")
	public Response updateVehiclePrice(@PathParam("vehicleID") Long userId, @PathParam("newPrice") Double newPrice) {
		try {
			Vehicle updateVehicle = vehicleService.updateVehiclePriceById(userId, newPrice);
			return Response.ok().entity(updateVehicle).build();
		} catch (VehicleException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

}

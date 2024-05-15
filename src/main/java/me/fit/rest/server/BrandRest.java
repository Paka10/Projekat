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
import me.fit.exception.BrandException;
import me.fit.model.Brand;
import me.fit.service.BrandService;

@Path("/api/brand")
public class BrandRest {

	@Inject
	private BrandService brandService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createBrand")
	@Operation(summary = "Web service which creates a Brand.", description = "Brand must be uniqe.")
	public Response createBrand(Brand brand) {
		Brand b = null;

		try {
			b = new BrandService().createBrand(brand);

		} catch (BrandException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok().entity(b).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllBrands")
	public Response getAllBrands() {
		List<Brand> brands = brandService.getAllBrands();
		return Response.ok().entity(brands).build(); 
	}
}

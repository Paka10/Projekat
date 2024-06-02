package me.fit.rest.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import me.fit.model.IPLog;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
@Path("/data")
@RegisterRestClient
public interface IPClient {
	@GET
	@Path("/client-ip")
	IPLog getIp();
}


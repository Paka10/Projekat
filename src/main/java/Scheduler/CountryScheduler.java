package Scheduler;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import me.fit.model.Country;
import me.fit.rest.client.CountryClient;
import me.fit.service.CountryService;

public class CountryScheduler {

	@Inject
	@RestClient
	private CountryClient countryClient;

	@Inject
	private CountryService countryService;

	@Scheduled(every = "60s")
	public void getAllCounries() {
		List<Country> countries = countryClient.getAll();
		countryService.saveCountries(countries);
	}
}
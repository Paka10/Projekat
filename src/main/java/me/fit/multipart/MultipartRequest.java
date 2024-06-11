package me.fit.multipart;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.ws.rs.core.MediaType;
import me.fit.multipart.MultipartResource.UploadSchema;

public class MultipartRequest {
	@RestForm("file")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	@Schema(implementation = UploadSchema.class)
	private FileUpload file;

	@RestForm("name")
	@PartType(MediaType.TEXT_PLAIN)
	private String name;

	@RestForm("lastName")
	@PartType(MediaType.TEXT_PLAIN)
	private String lastName;

	@RestForm("email")
	@PartType(MediaType.TEXT_PLAIN)
	private String email;

	@RestForm("countryName")
	@PartType(MediaType.TEXT_PLAIN)
	private String countryName;

	@RestForm("phoneNumbers")
	@PartType(MediaType.TEXT_PLAIN)
	private List<String> phoneNumbers;

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public FileUpload getFile() {
		return file;
	}

	public void setFile(FileUpload file) {
		this.file = file;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

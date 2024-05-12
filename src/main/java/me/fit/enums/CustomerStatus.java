package me.fit.enums;

public enum CustomerStatus {
	EXISTS("Customer already exists");
	
	private String label;

	private CustomerStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	 
}
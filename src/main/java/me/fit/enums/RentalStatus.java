package me.fit.enums;

public enum RentalStatus {
	EXISTS("Rental already exists!");

	private String label;

	private RentalStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}

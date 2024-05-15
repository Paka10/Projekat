package me.fit.enums;

public enum VehicleStatus {
EXISTS("Vehicle already exists");
	
	private String label;

	private VehicleStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}

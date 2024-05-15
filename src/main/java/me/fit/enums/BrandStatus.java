package me.fit.enums;

public enum BrandStatus {

	EXISTS("Brand already exists!");

	private String label;

	private BrandStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}

package com.hr.corp.models;

public class ConversionDetails {
	private String fromUnit;
	private float fromValue;
	private String toUnit;
	private float toValue;

	public String getFromUnit() {
		return fromUnit;
	}

	public void setFromUnit(String fromUnit) {
		this.fromUnit = fromUnit;
	}

	public float getFromValue() {
		return fromValue;
	}

	public void setFromValue(float fromValue) {
		this.fromValue = fromValue;
	}

	public String getToUnit() {
		return toUnit;
	}

	public void setToUnit(String toUnit) {
		this.toUnit = toUnit;
	}

	public float getToValue() {
		return toValue;
	}

	public void setToValue(float toValue) {
		this.toValue = toValue;
	}

	@Override
	public String toString() {
		return "ConversionDetails [fromUnit=" + fromUnit + ", fromValue=" + fromValue + ", toUnit=" + toUnit
				+ ", toValue=" + toValue + "]";
	}

	public ConversionDetails() {
		super();
	}

}

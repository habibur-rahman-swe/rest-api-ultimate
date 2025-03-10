package com.hr.corp.converters;

import com.hr.corp.models.ConversionDetails;

public class LengthConverter {
	void kilometer2Mile(ConversionDetails details) {
		float km = details.getFromValue();
		float miles = km * 0.621371f;
		
		details.setToValue(miles);
	}
	
	void mile2Kilometer(ConversionDetails details) {
		float miles = details.getFromValue();
		float km = 1.60934f * miles;
		
		details.setToValue(km);
	}
}

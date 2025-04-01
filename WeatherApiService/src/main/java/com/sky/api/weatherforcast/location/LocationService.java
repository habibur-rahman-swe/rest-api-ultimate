package com.sky.api.weatherforcast.location;

import org.springframework.stereotype.Service;

import com.sky.api.weatherforcast.common.Location;

@Service
public class LocationService {

	private LocationRepository repository;
	
	public LocationService(LocationRepository repository) {
		super();
		this.repository = repository;
	}
	
	public Location add(Location location) {
		return repository.save(location);
	}
	
}

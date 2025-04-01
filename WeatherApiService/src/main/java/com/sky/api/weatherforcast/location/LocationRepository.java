package com.sky.api.weatherforcast.location;

import org.springframework.data.repository.CrudRepository;

import com.sky.api.weatherforcast.common.Location;

public interface LocationRepository extends CrudRepository<Location, String> {

}

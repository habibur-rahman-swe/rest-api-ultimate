package com.sky.api.weatherforcast.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sky.api.weatherforcast.common.Location;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class LocationRepositoryTest {

	@Autowired
	private LocationRepository repository;
	
	@Test
	void testAddSuccess() {
		Location location = new Location();
		location.setCode("NYD_USA");
		location.setCityName("New Yourk City");
		location.setRegionName("New Yourk");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		Location savedLocation = repository.save(location);
		
		assertThat(savedLocation).isNotNull();
		assertThat(savedLocation.getCode()).isEqualTo("NYD_USA");
	}

	@Test
	public void testListSuccess() {
		List<Location> locations = repository.findUntrashed();
		
		assertThat(locations).isNotEmpty();
		
		locations.forEach(System.out::println);
	}
	
	@Test
	public void testGetNotFound() {
		String code = "ABCD";
		Location location = repository.findByCode(code);
		
		assertThat(location).isNull();
	}
	
	@Test
	public void testGetSuccess() {
		String code = "NYC_USA";
		Location location = repository.findByCode(code);
		
		assertThat(location).isNotNull();
		assertThat(location.getCode()).isEqualTo(code);
	}
	
	@Test
	public void testTrashSuccess() {
		String code = "NYC_USA";
		
		repository.trashByCode(code);
		
		Location location = repository.findByCode(code);
		assertThat(location).isNull();
	}
}

package com.sky.api.weatherforcast.location;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.api.weatherforcast.common.Location;

@WebMvcTest(LocationApiController.class)
class LocationApiControllerTest {

	private static final String END_POINT_PATH = "/v1/locations";

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockitoBean
	LocationService service;

	@Test
	public void testAddShouldReturn400BadRequest() throws Exception {
		Location location = new Location();

		String bodyContent = mapper.writeValueAsString(location);
		mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	public void testAddShouldReturn201Created() throws Exception {
		Location location = new Location();
		location.setCode("NYC_USA");
		location.setCityName("New Yourk City");
		location.setRegionName("New Yourk");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);

		when(service.add(location)).thenReturn(location);

		String bodyContent = mapper.writeValueAsString(location);

		mockMvc.perform(post(END_POINT_PATH)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(bodyContent))
		    .andExpect(status().isCreated())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("$.code").value(location.getCode()))
		    .andDo(print());
	}
}
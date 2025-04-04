package com.sky.api.weatherforcast.location;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
	
	@Test
	public void testListShouldReturn204NoContent() throws Exception {
		Mockito.when(service.list()).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get(END_POINT_PATH))
		
		.andExpect(status().isNoContent())
		.andDo(print());
	}
	
	@Test
	public void testListShouldReturn200OK() throws Exception {
		Location location1 = new Location();
		location1.setCode("NYC_CD");
		location1.setCityName("New Yourk City");
		location1.setRegionName("New Yourk");
		location1.setCountryCode("US");
		location1.setCountryName("United States of America");
		location1.setEnabled(true);

		Location location2 = new Location();
		location2.setCode("NYK_CD");
		location2.setCityName("New Yourk City");
		location2.setRegionName("New Yourk");
		location2.setCountryCode("US");
		location2.setCountryName("United States of America");
		location2.setEnabled(true);

		when(service.list()).thenReturn(List.of(location1, location2));
		
		mockMvc.perform(get(END_POINT_PATH))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		    .andDo(print());
	}
	
	@Test
	@DisplayName("Get Should Return 405 Method Not Allowed")
	public void testGetShouldReturn405MethodNotAllowed() throws Exception {
		String requestURL = END_POINT_PATH + "/ABCDE";
		
		mockMvc.perform(post(requestURL))
			.andExpect(status().isMethodNotAllowed())
			.andDo(print());
	}
	
	@Test
	@DisplayName("Get Should Return 404 Method Not Found")
	public void testGetShouldReturn404MethodNotFound() throws Exception {
		String requestURL = END_POINT_PATH + "/ABCDE";
		
		mockMvc.perform(get(requestURL))
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	@Test
	@DisplayName("Get Should Return 200 OK")
	public void testGetShouldReturn200OK() throws Exception {
		String code = "NYC_CD";
		String requestURL = END_POINT_PATH + "/" + code;
		
		Location location = new Location();
		location.setCode("NYC_CD");
		location.setCityName("New Yourk City");
		location.setRegionName("New Yourk");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		when(service.get(code)).thenReturn(location);
		
		mockMvc.perform(get(requestURL))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.code",is(code)));
	}
	
	@Test
	public void testUpdateshouldReturn404NotFound() throws Exception {
		String code = "ABCD";
		Location location = new Location();
		location.setCode(code);
		location.setCityName("New Yourk City");
		location.setRegionName("New Yourk");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		when(service.update(location)).thenThrow(new LocationNotFoundException("No location found!"));
		
		String bodyContend = mapper.writeValueAsString(location);
		
		mockMvc.perform(put(END_POINT_PATH).contentType(MediaType.APPLICATION_JSON).content(bodyContend))
		.andExpect(status().isNotFound())
		.andDo(print());

	}
	
	@Test
	public void testUpdateshouldReturn400BadRequest() throws Exception {
		Location location = new Location();
		location.setCityName("New Yourk City");
		location.setRegionName("New Yourk");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		when(service.update(location)).thenThrow(new LocationNotFoundException("No location found!"));
		
		String bodyContend = mapper.writeValueAsString(location);
		
		mockMvc.perform(put(END_POINT_PATH).contentType(MediaType.APPLICATION_JSON).content(bodyContend))
		.andExpect(status().isBadRequest())
		.andDo(print());

	}
	
	
	@Test
	public void testUpdateshouldReturn200OK() throws Exception {
		String code = "NYC_USA";
		Location location = new Location();
		location.setCode(code);
		location.setCityName("New Yourk City");
		location.setRegionName("New Yourk");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		when(service.update(location)).thenReturn(location);
		
		String bodyContend = mapper.writeValueAsString(location);
		
		mockMvc.perform(put(END_POINT_PATH).contentType(MediaType.APPLICATION_JSON).content(bodyContend))
		.andExpect(status().isOk())
		.andDo(print());

	}

}
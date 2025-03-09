package com.sameer.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sameer.weather.service.OpenWeatherMapService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1")
@Slf4j
public class OpenWeatherMapController {
	
	@Autowired private OpenWeatherMapService openWeatherMapService;
	
	  @GetMapping("/weather") 
	  public ResponseEntity<?> getWeatherByCity(@RequestParam String city) throws JsonMappingException, JsonProcessingException {
		  log.info("In Open Weather Service");
		  return openWeatherMapService.getWeatherDetails(city);
		  }

}

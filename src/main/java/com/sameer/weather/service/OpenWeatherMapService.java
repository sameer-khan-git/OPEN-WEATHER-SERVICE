package com.sameer.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sameer.weather.dto.Error;
import com.sameer.weather.dto.OpenWeatherDto;
import com.sameer.weather.dto.WeatherDetails;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OpenWeatherMapService {
	
	private final WebClient webClient;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${accesskey}")
	String accessKey;
	
	  public OpenWeatherMapService(WebClient.Builder webClientBuilder) {
		  log.info("OWS : Web Client initialized");
	        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5").build();
	    }
	

	public ResponseEntity<?> getWeatherDetails(String city) throws JsonMappingException, JsonProcessingException {
    	String jsonresponse = fetchJsonResponse(city).block();
    	System.out.println(jsonresponse);
    	
    	OpenWeatherDto openWeatherDto = objectMapper.readValue(jsonresponse,OpenWeatherDto.class);
    	
    	
    	if(openWeatherDto.getMain()==null ||openWeatherDto.getWind()==null)
    	{
  		  Error error = Error.builder()
				  .code(404)
				  .type("Invalid Query String")
				  .info("No data is available for the given city or invalid city name").build();
  		  
  		  log.info("OWS : Error Response received!");
		  return ResponseEntity.badRequest().body(error);
    		
    	}else
    	{
    	 	//Wind Speed in the Payload is in Meter/Sec. Converted to Kilometer/hr
        	// Temperature is in Kelvin. Converted to Celsius
        	WeatherDetails weatherDetails = WeatherDetails.builder()
        			.wind_Speed((int)(openWeatherDto.getWind().getSpeed() * 3.6))
        			.temperature_degrees((int)(openWeatherDto.getMain().getTemp() - 273.15))
        			.build();
        	log.info("OWS : Received Successfull Response!");
        	return ResponseEntity.ok(weatherDetails);
    		
    	}
    	
	}
    public Mono<String> fetchJsonResponse(String city) {
    	
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("q", city+",AU")
                        .queryParam("appid", accessKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

}

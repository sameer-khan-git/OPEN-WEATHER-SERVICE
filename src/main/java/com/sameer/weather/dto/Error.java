package com.sameer.weather.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    	private boolean success;
	    public int code;
	    public String type;
	    public String info;
}

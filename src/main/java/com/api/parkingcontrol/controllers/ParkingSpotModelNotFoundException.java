package com.api.parkingcontrol.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ParkingSpotModelNotFoundException extends RuntimeException {
    public ParkingSpotModelNotFoundException(String message){
        super(message);
    }

}

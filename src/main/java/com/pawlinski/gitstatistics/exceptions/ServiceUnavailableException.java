package com.pawlinski.gitstatistics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class ServiceUnavailableException extends RuntimeException{

    private ServiceUnavailableException(String message){
        super(message);
    }

    public static ServiceUnavailableException standard(){
        return new ServiceUnavailableException("Service is currently unavailable");
    }
}

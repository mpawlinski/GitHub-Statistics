package com.pawlinski.gitstatistics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    private UserNotFoundException(String name){
        super(name);
    }

    public static UserNotFoundException forName(String username){
        return new UserNotFoundException(String.format("User with name %s was not found", username));
    }
}

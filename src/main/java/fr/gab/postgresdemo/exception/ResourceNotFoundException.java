package fr.gab.postgresdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {
    private String message;

    public ResourceNotFoundException(String message){
        this.message = message;
    }


    public ResourceNotFoundException() {
        this.message = "Resource was not found.";
    }


    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
package com.ibm.academia.ruletaapirest.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, Long id) {
        super(message);
    }

    
}

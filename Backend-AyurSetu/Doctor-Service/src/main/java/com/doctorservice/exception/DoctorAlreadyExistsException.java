package com.doctorservice.exception;
 
public class DoctorAlreadyExistsException extends RuntimeException {
    public DoctorAlreadyExistsException(String message) {
        super(message);
    }
} 
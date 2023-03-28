package com.kaptain.oouthapp.oouthapp.exceptions;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException() {
        super("Doctor does not exist!");
    }
}

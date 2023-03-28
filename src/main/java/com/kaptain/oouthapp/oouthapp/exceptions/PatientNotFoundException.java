package com.kaptain.oouthapp.oouthapp.exceptions;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException() {
        super("Patient does not exist!");
    }
}

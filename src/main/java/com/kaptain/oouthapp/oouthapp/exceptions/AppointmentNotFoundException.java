package com.kaptain.oouthapp.oouthapp.exceptions;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException() {
        super("Appointment Not Found!");
    }
}

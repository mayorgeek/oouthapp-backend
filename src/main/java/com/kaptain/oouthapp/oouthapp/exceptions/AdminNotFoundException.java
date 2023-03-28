package com.kaptain.oouthapp.oouthapp.exceptions;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException() {
        super("Admin does not exist!");
    }
}

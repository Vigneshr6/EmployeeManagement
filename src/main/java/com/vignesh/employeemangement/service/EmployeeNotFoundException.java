package com.vignesh.employeemangement.service;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(long id) {
        super(String.format("Employee id:{} not found",id));
    }
}

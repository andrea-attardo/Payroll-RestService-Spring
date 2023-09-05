package com.andreanesos.payroll;

public class EmployeeNotFoundException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    EmployeeNotFoundException(Long id){
        super("Coundt find  employee " + id);
    }
}

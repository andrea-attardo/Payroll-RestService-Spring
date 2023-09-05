package com.andreanesos.payroll;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeRepository repository;
    
    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }
    
    
    @GetMapping
    List<Employee> findAll(){
        return repository.findAll();
    }
    
    @PostMapping
     Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }
    
    //single items
    @GetMapping("/{id}")
    Employee findById(@PathVariable Long id) throws EmployeeNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
    
    @PutMapping("/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }
    
    @DeleteMapping("{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
}
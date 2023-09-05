package com.andreanesos.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    private EmployeeModelAssembler assembler;
    
    EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    
    
    @GetMapping
    CollectionModel<EntityModel<Employee>> findAll() {

        List<EntityModel<Employee>> employees = repository.findAll().stream() //
                            .map(assembler::toModel) //
                            .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel());
    }
    
    @PostMapping
     Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }
    
    //single items
    @GetMapping("/{id}")
    EntityModel<Employee> findById(@PathVariable Long id)  {

        Employee employee = null;
        try {
            employee = repository.findById(id) //
                    .orElseThrow(() -> new EmployeeNotFoundException(id));
        } catch (EmployeeNotFoundException e) {
        }
        
        return assembler.toModel(employee);
        
        /*
        EntityModel<Employee> entityModel= EntityModel.of(null);
        Optional<Employee> opEmployee = repository.findById(id);
        try {
            if (opEmployee.isPresent()) {
            entityModel = assembler.toModel(opEmployee.get());
            }else {
                throw new  EmployeeNotFoundException(id);
            }
        }catch(EmployeeNotFoundException e) {
            
        }
        return entityModel;
        */
        //        .orElseThrow(() -> new EmployeeNotFoundException(id));

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

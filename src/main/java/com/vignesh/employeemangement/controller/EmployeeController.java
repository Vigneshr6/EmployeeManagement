package com.vignesh.employeemangement.controller;

import com.vignesh.employeemangement.model.Employee;
import com.vignesh.employeemangement.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> all = (List<Employee>) employeeRepository.findAll();
        log.debug("count : "+all.size());
        return all;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Employee save(@RequestBody Employee employee){
        log.debug("Employee : {}",employee);
        employeeRepository.save(employee);
        return employee;
    }

    @PutMapping(value = "/{id}",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Employee employee){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()){
            log.error("Employee with id : {}  not found",id);
            return new ResponseEntity(NOT_FOUND);
        }
        Employee empOld = optionalEmployee.get();
        empOld.setFirstName(employee.getFirstName());
        empOld.setLastName(employee.getLastName());
        empOld.setDob(employee.getDob());
        empOld.setSalary(employee.getSalary());
        employeeRepository.save(empOld);
        return new ResponseEntity(empOld,OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            employeeRepository.deleteById(id);
            return new ResponseEntity(OK);
        } catch (EmptyResultDataAccessException e) {
            log.error("Employee with id : {}  not found",id);
            return new ResponseEntity(NOT_FOUND);
        }
    }
}

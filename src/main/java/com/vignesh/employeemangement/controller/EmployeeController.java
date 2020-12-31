package com.vignesh.employeemangement.controller;

import com.vignesh.employeemangement.model.Employee;
import com.vignesh.employeemangement.service.EmployeeNotFoundException;
import com.vignesh.employeemangement.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> all = (List<Employee>) employeeService.findAll();
        log.debug("count : "+all.size());
        return all;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Employee save(@RequestBody Employee employee){
        log.debug("Employee : {}",employee);
        employeeService.save(employee);
        return employee;
    }

    @PutMapping(value = "/{id}",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Employee employee){
        Employee empOld;
        try {
            empOld = employeeService.update(id,employee);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity(NOT_FOUND);
        }
        return new ResponseEntity(empOld,OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            employeeService.delete(id);
            return new ResponseEntity(OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity(NOT_FOUND);
        }
    }
}

package com.vignesh.employeemangement.service;

import com.vignesh.employeemangement.model.Employee;
import com.vignesh.employeemangement.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll() {
        List<Employee> all = (List<Employee>) employeeRepository.findAll();
        log.debug("count : "+all.size());
        return all;
    }

    public Employee save(Employee employee){
        log.debug("Employee : {}",employee);
        employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public Employee update(long id, Employee employee) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findByIdForUpdate(id);
        if(optionalEmployee.isEmpty()){
            log.error("Employee with id : {}  not found",id);
            throw new EmployeeNotFoundException(id);
        }
        Employee empOld = optionalEmployee.get();
        empOld.setFirstName(employee.getFirstName());
        empOld.setLastName(employee.getLastName());
        empOld.setDob(employee.getDob());
        empOld.setSalary(employee.getSalary());
        employeeRepository.save(empOld);
        return empOld;
    }

    public void delete(Long id) throws EmployeeNotFoundException {
        try {
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Employee with id : {}  not found",id);
            throw new EmployeeNotFoundException(id);
        }
    }
}

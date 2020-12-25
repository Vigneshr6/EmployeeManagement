package com.vignesh.employeemangement.repository;

import com.vignesh.employeemangement.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}

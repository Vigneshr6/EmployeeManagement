package com.vignesh.employeemangement.repository;

import com.vignesh.employeemangement.model.Employee;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    @Query("select e from Employee e where e.id=:id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
// PESSIMISTIC_WRITE locks other threads from updating the row
    Optional<Employee> findByIdForUpdate(Long id);
}

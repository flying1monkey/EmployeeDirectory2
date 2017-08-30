package com.hiwotab.employeedirectory.repositories;

import com.hiwotab.employeedirectory.models.Department;
import com.hiwotab.employeedirectory.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface EmployeeRepo extends CrudRepository<Employee,Long>{
    Set<Employee> findByDepartment(Department department);
}


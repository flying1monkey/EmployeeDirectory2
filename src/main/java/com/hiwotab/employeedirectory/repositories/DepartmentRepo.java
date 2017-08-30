package com.hiwotab.employeedirectory.repositories;

import com.hiwotab.employeedirectory.models.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepo extends CrudRepository<Department,Long> {
}

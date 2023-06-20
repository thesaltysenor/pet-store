package com.promineotech.petstore.dao;

import com.promineotech.petstore.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}

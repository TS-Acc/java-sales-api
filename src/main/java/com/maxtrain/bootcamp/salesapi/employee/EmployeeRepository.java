package com.maxtrain.bootcamp.salesapi.employee;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
	Optional<Employee> findByEmail(String email);
	Optional<Employee> findByEmailAndPassword(String email, String password);
}

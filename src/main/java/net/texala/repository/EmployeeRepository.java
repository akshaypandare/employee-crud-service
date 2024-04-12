package net.texala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.texala.enums.Status;
import net.texala.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

	@Query(value = "Update Employee set empStatus=:status where empId =:id")
	@Modifying
	int updateStatus(Status status, int id);
}
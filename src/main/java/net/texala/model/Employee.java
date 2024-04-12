package net.texala.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import net.texala.enums.Status;

@Getter
@Setter
@Entity
@Table(name = "employee_management")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "emp_id", length = 4, nullable = false, updatable = false)
	@SequenceGenerator(name = "empId_seq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(generator = "empId_seq", strategy = GenerationType.SEQUENCE)
	private int empId;

	@Column(name = "emp_name", length = 20, nullable = true)
	private String empName;

	@Column(name = "emp_address", length = 50, nullable = true)
	private String empAddress;

	@Column(name = "emp_salary", length = 5, nullable = true)
	private double empSalary;

	@Column(name = "emp_status", length = 15, nullable = true)
	@Enumerated(EnumType.STRING)
	private Status empStatus;

}
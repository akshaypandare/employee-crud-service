package net.texala.web.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import net.texala.enums.Status;

@Getter
@Setter
public class EmployeeVo {

	@NotNull(message = "ID is required.")
	private int empId;

	@NotBlank(message = "Name is required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contains only string.")
	private String empName;

	@NotBlank(message = "Address is required")
	private String empAddress;

	//@Size(min = 1, max = 200000, message = "Salary should required in range.")
	@Min(value = 1)
	@Max(value = 250000)
	private double empSalary;

	@NotNull(message = "Status is required")
	private Status empStatus;
}
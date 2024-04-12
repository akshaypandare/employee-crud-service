package net.texala.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.texala.enums.Status;
import net.texala.web.vo.EmployeeVo;

public interface EmployeeService {

	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public List<EmployeeVo> fetchAll();

	public EmployeeVo fetchById(int empId);

	public EmployeeVo add(EmployeeVo employeeVo);

	public EmployeeVo update(EmployeeVo employeeVo);

	public void update(Status status, int empId);
	
	public void delete(int empId);
}
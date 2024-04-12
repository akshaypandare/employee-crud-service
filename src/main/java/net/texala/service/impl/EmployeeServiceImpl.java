package net.texala.service.impl;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.texala.constants.Utility;
import net.texala.enums.Status;
import net.texala.exception.ServiceException;
import net.texala.model.Employee;
import net.texala.repository.EmployeeRepository;
import net.texala.service.EmployeeService;
import net.texala.specification.SearchSpecification;
import net.texala.web.mappers.EmployeeMapper;
import net.texala.web.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {

		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Employee> specification = SearchSpecification.searchEmployee(filterBy, searchText);
		return repo.findAll(specification, pageable).map(employeeMapper::toVo);
	}

	@Override
	public List<EmployeeVo> fetchAll() {
		return employeeMapper.toVoes(repo.findAll());
	}

	@Override
	public EmployeeVo fetchById(int empId) {
		return employeeMapper.toVo(findById(empId));
	}
	
	private Employee findById(int empId) {
		return repo.findById(empId).orElseThrow(()-> new ServiceException("Employee id not present"));
	}

	@Override
	@Transactional
	public EmployeeVo add(EmployeeVo employeeVo) {
		final Employee employee = employeeMapper.toEntity(employeeVo);
		return employeeMapper.toVo(repo.save(employee));
	}

	@Override
	@Transactional
	public EmployeeVo update(EmployeeVo employeeVo) {
		Objects.requireNonNull(employeeVo.getEmpId());
		findById(employeeVo.getEmpId());
		final Employee employee = employeeMapper.toEntity(employeeVo);
		return employeeMapper.toVo(repo.save(employee));
	}

	@Override
	@Transactional
	public void update(Status status, int empId) {
		findById(empId);
		repo.updateStatus(status, empId);
	}
	
	@Override
	@Transactional
	public void delete(int empId) {
		findById(empId);
		repo.deleteById(empId);
	}
}
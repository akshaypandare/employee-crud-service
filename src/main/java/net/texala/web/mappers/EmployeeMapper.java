package net.texala.web.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import net.texala.model.Employee;
import net.texala.web.vo.EmployeeVo;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	public EmployeeVo toVo(Employee employee);

	public List<EmployeeVo> toVoes(List<Employee> list);

	public Employee toEntity(EmployeeVo employeeVo);

}
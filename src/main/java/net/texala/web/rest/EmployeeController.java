package net.texala.web.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import net.texala.constants.Constants;
import net.texala.enums.Status;
import net.texala.response.entity.RestResponse;
import net.texala.response.entity.RestStatus;
import net.texala.service.impl.EmployeeServiceImpl;
import net.texala.web.vo.EmployeeVo;

@RestController
@RequestMapping(value = "/v0/employee")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeServiceImpl employeeService;

	/**
	 * Search a list of available Employee
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param filterBy
	 * @param searchText
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<RestResponse<Page<EmployeeVo>>> search(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "" + Integer.MAX_VALUE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "empId:desc", required = false) String sortBy,
			@RequestParam(value = "filterBy", required = false) String filterBy,
			@RequestParam(value = "searchText", required = false) String searchText) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, Constants.FETCH_RECORDS);
		final Page<EmployeeVo> page = employeeService.search(pageNo, pageSize, sortBy, filterBy, searchText);
		final RestResponse<Page<EmployeeVo>> response = new RestResponse<>(page, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Fetching all records from the database
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<RestResponse<List<EmployeeVo>>> fetchAll() {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, Constants.FETCH_RECORDS);
		final List<EmployeeVo> list = employeeService.fetchAll();
		final RestResponse<List<EmployeeVo>> response = new RestResponse<>(list, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Fetching record based on Employee ID from the database
	 * 
	 * @param empId
	 * @return
	 */
	@GetMapping("/{empId}")
	public ResponseEntity<RestResponse<EmployeeVo>> fetchById(@PathVariable(name = "empId") int empId) {
		
		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, Constants.FETCH_RECORDS);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.fetchById(empId), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Update Employee Status based on Employee ID
	 * 
	 * @param employeeVo
	 * @return
	 */
	@PostMapping
	public ResponseEntity<RestResponse<EmployeeVo>> add(@Valid @RequestBody EmployeeVo employeeVo) {
		
		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.CREATED, Constants.ADD_RECORD);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.add(employeeVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * Update Employee record based on Employee ID
	 * 
	 * @param empId
	 * @param employeeVo
	 * @return
	 */
	@PutMapping("/{empId}")
	public ResponseEntity<RestResponse<EmployeeVo>> update(@PathVariable(name = "empId") int empId,
			@Valid @RequestBody EmployeeVo employeeVo) {

		employeeVo.setEmpId(empId);
		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, Constants.UPDATE_RECORD);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Update data based on Employee ID
	 * 
	 * @param empId
	 * @param status
	 * @return
	 */
	@PatchMapping("/{empId}")
	public ResponseEntity<RestResponse<Void>> updateStatus(@PathVariable(name = "empId") int empId,
			@RequestParam(name = "empStatus") Status status) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, Constants.UPDATE_RECORD);
		employeeService.update(status, empId);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Delete records based on Employee ID
	 * 
	 * @param empId
	 * @return
	 */
	@DeleteMapping("/{empId}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "empId") int empId) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, Constants.DELETE_MSG);
		employeeService.delete(empId);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
package net.texala.web.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import net.texala.constants.Constants;
import net.texala.enums.Status;
import net.texala.response.entity.RestResponse;
import net.texala.service.impl.EmployeeServiceImpl;
import net.texala.web.vo.EmployeeVo;

@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController controller;

	@Mock
	private EmployeeServiceImpl service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * 	
	 */
	@Test
	public void testSearch() {
		when(service.search(anyInt(), anyInt(), anyString(), anyString(), anyString())).thenReturn(createPage());
		
		ResponseEntity<RestResponse<Page<EmployeeVo>>> responseEntity = controller.search(1, 10, "empSalary:desc", "empName:Akshay", "empAddress");
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		RestResponse<Page<EmployeeVo>> response = responseEntity.getBody();
		assertNotNull(response);
		assertEquals(Constants.FETCH_RECORDS, response.getStatus().getMessage());
		
	}

	private Page<EmployeeVo> createPage() {
		Page<EmployeeVo> page = new PageImpl<>(createEmployeeList());
		return page;
	}

	/**
	 * 	Unit Test Case for Fetch All records
	 */
	@Test
	public void testFetchAll() {
		when(service.fetchAll()).thenReturn(createEmployeeList());
		
		ResponseEntity<RestResponse<List<EmployeeVo>>> responseEntity = controller.fetchAll();
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		RestResponse<List<EmployeeVo>> response = responseEntity.getBody();
		assertNotNull(response);
		assertEquals(Constants.FETCH_RECORDS, response.getStatus().getMessage());
		
		final List<EmployeeVo> list = response.getData();
		assertNotNull(list);
		assertEquals(list.size(), responseEntity.getBody().getData().size());
	}

	private List<EmployeeVo> createEmployeeList() {

		EmployeeVo employeeVo1 = new EmployeeVo();
		EmployeeVo employeeVo2 = new EmployeeVo();
		List<EmployeeVo> list = new ArrayList<>();
		employeeVo1.setEmpId(1001);
		employeeVo2.setEmpId(1002);
		list.add(employeeVo1);
		list.add(employeeVo2);
		return list;
	}

	/**
	 * 	Unit Test Case for Fetch Record By Id.
	 */
	@Test
	public void testFetchById() {
		when(service.fetchById(ArgumentMatchers.anyInt())).thenReturn(createEmployee());
		
		ResponseEntity<RestResponse<EmployeeVo>> responseEntity = controller.fetchById(anyInt());
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		RestResponse<EmployeeVo> response = responseEntity.getBody();
		assertNotNull(response);
		assertEquals(Constants.FETCH_RECORDS, response.getStatus().getMessage());

		EmployeeVo employeeVo = response.getData();
		assertNotNull(employeeVo);
		assertEquals(employeeVo.getEmpId(), responseEntity.getBody().getData().getEmpId());
	}

	private EmployeeVo createEmployee() {
		EmployeeVo employeeVo = new EmployeeVo();
		employeeVo.setEmpId(1001);
		employeeVo.setEmpName("Akshay");
		employeeVo.setEmpAddress("Shirur");
		employeeVo.setEmpSalary(25252);
		employeeVo.setEmpStatus(Status.ACTIVATED);
		return employeeVo;
	}

	/**
	 * 	Unit Test Case for add record
	 */
	@Test
	public void testAdd() {
		when(service.add(any(EmployeeVo.class))).thenReturn(createEmployee());
		
		ResponseEntity<RestResponse<EmployeeVo>> responseEntity = controller.add(createEmployee());
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		RestResponse<EmployeeVo> response = responseEntity.getBody();
		assertNotNull(response);
		assertEquals(Constants.ADD_RECORD, response.getStatus().getMessage());

		EmployeeVo employeeVo = response.getData();
		assertNotNull(employeeVo);
		assertEquals(employeeVo.getEmpId(), responseEntity.getBody().getData().getEmpId());
		
	}

	/**
	 * Unit Test Case for update record
	 */
	@Test
	public void testUpdate() {
		when(service.update(any(EmployeeVo.class))).thenReturn(createEmployee());
		
		ResponseEntity<RestResponse<EmployeeVo>> responseEntity = controller.update(anyInt(),createEmployee());
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		RestResponse<EmployeeVo> response = responseEntity.getBody();
		assertNotNull(response);
		assertEquals(Constants.UPDATE_RECORD, response.getStatus().getMessage());

		EmployeeVo employeeVo = response.getData();
		assertNotNull(employeeVo);
		assertEquals(employeeVo.getEmpId(), responseEntity.getBody().getData().getEmpId());
		
	}

	/**
	 * 	Unit Test Case for update status
	 */
	@Test
	public void testUpdateStatus() {
		doNothing().when(service).update(any(Status.class),anyInt());
		
		ResponseEntity<RestResponse<Void>> responseEntity = controller.updateStatus(anyInt(),any(Status.class));
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		RestResponse<Void> response = responseEntity.getBody();
		assertNotNull(response);
		assertEquals(Constants.UPDATE_RECORD, response.getStatus().getMessage());

		Void employeeVo = response.getData();
		assertNull(employeeVo);
	}
	
	/**
	 * 	Unit Test
	 */
	@Test
	public void testDelete() {
		doNothing().when(service).delete(anyInt());
		
		ResponseEntity<RestResponse<Void>> responseEntity = controller.delete(anyInt());
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		RestResponse<Void> response = responseEntity.getBody();
		assertNotNull(response);
		assertEquals(Constants.DELETE_MSG, response.getStatus().getMessage());

		Void employeeVo = response.getData();
		assertNull(employeeVo);
	}
}
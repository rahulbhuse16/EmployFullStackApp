package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class EmployeeController implements ErrorController {
	private static final String path="/error";
 @Autowired
 private EmployeeRepository employeeRepo;
	@GetMapping("/employees")
	public  List<Employee>getAllEmployees(){
		return employeeRepo.findAll();
	}
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepo.save(employee);
	}
	@RequestMapping(value = path,method=RequestMethod.GET)
	public String getError() {
		return "not found";
	}
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Employee not exist with id :" + id));
		
		employeeRepo.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Employee not exist with id :" + id));
		
		employee.setFirstname(employeeDetails.getFirstname());
		employee.setFirstname(employeeDetails.getLastname());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepo.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Employee not exist with id :" + id));
		return ResponseEntity.ok(employee);
	}
	
	
	public String getErrorPath() {
		return path;
	}
	
}

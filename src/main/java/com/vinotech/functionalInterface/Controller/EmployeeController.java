package com.vinotech.functionalInterface.Controller;

import com.vinotech.functionalInterface.Model.Employee;
import com.vinotech.functionalInterface.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Endpoint to save an employee
    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee.accept(employee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Employee created successfully: " + employee.getName());
    }

    // Endpoint to get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees.get();
        return ResponseEntity.ok(employees);
    }

    // Endpoint to get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById.apply(id);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
        return ResponseEntity.ok(employee);
    }

    // Endpoint to update an employee
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee.apply(id, employeeDetails);
        if (updatedEmployee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: " + id); // 404 Not Found
        }
        return ResponseEntity.ok("Employee updated successfully: " + updatedEmployee.getName());
    }

    // Endpoint to check if an employee exists
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> doesEmployeeExist(@PathVariable Long id) {
        boolean exists = employeeService.doesEmployeeExist.test(id);
        return ResponseEntity.ok(exists);
    }

    // Endpoint to check if an employee's salary is greater than a specified amount
    @GetMapping("/{id}/salaryGreaterThan/{salary}")
    public ResponseEntity<Boolean> isSalaryGreaterThan(@PathVariable Long id, @PathVariable Double salary) {
        boolean result = employeeService.isSalaryGreaterThan.test(id, salary);
        return ResponseEntity.ok(result);
    }

    // Endpoint to increment an employee's salary
    @PutMapping("/incrementSalary")
    public ResponseEntity<Employee> incrementSalary(@RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.incrementSalary.apply(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Endpoint to merge two employees
    @PostMapping("/merge")
    public ResponseEntity<Employee> mergeEmployees(@RequestBody List<Employee> employees) {
        if (employees.size() != 2) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
        Employee mergedEmployee = employeeService.mergeEmployees.apply(employees.get(0), employees.get(1));
        return ResponseEntity.ok(mergedEmployee);
    }
}


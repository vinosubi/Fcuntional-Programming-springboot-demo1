package com.vinotech.functionalInterface.Service;

import com.vinotech.functionalInterface.Model.Employee;
import com.vinotech.functionalInterface.Repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.*;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // Consumer: Accepts an Employee object and saves it to the database
    public Consumer<Employee> saveEmployee = (employee) -> {
        employeeRepository.save(employee);
        logger.info("Saved Employee: {}", employee);  // Logging instead of System.out.println
    };

    // Supplier: Returns a list of all Employee objects from the database
    public Supplier<List<Employee>> getAllEmployees = () -> {
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Fetched Employees: {}", employees);  // Logging instead of System.out.println
        return employees;
    };

    // Function: Accepts an ID and returns the corresponding Employee object
    public Function<Long, Employee> getEmployeeById = (id) -> {
        Employee employee = employeeRepository.findById(id).orElse(null);
        logger.info("Fetched Employee by ID: {}", employee);  // Logging instead of System.out.println
        return employee;
    };

    // BiFunction: Accepts an ID and Employee object, updates the existing Employee, and returns the updated Employee
    public BiFunction<Long, Employee, Employee> updateEmployee = (id, employeeDetails) -> {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setName(employeeDetails.getName());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setSalary(employeeDetails.getSalary());
            employee.setAge(employeeDetails.getAge());
            employeeRepository.save(employee);
            logger.info("Updated Employee: {}", employee);  // Logging instead of System.out.println
        }
        return employee;
    };

    // Predicate: Checks if an Employee with the given ID exists
    public Predicate<Long> doesEmployeeExist = (id) -> {
        boolean exists = employeeRepository.existsById(id);
        logger.info("Does Employee with ID {} exist? {}", id, exists);  // Logging instead of System.out.println
        return exists;
    };

    // BiPredicate: Accepts an ID and a salary, returns true if the Employee's salary is greater than the provided salary
    public BiPredicate<Long, Double> isSalaryGreaterThan = (id, salary) -> {
        Employee employee = employeeRepository.findById(id).orElse(null);
        boolean result = employee != null && employee.getSalary() > salary;
        logger.info("Is salary of Employee ID {} greater than {}? {}", id, salary, result);  // Logging instead of System.out.println
        return result;
    };

    // UnaryOperator: Accepts an Employee object, increments its salary, saves it, and returns the updated Employee
    public UnaryOperator<Employee> incrementSalary = (employee) -> {
        employee.setSalary(employee.getSalary() + 1000);
        employeeRepository.save(employee);
        logger.info("Incremented Salary for Employee: {}", employee);  // Logging instead of System.out.println
        return employee;
    };

    // BinaryOperator: Merges two Employee objects into a new Employee object
    public BinaryOperator<Employee> mergeEmployees = (e1, e2) -> {
        Employee mergedEmployee = new Employee();
        mergedEmployee.setName(e1.getName() + " & " + e2.getName());
        mergedEmployee.setDepartment(e1.getDepartment());
        mergedEmployee.setSalary(e1.getSalary() + e2.getSalary());
        mergedEmployee.setAge(Math.max(e1.getAge(), e2.getAge()));
        employeeRepository.save(mergedEmployee);
        logger.info("Merged Employee: {}", mergedEmployee);  // Logging instead of System.out.println
        return mergedEmployee;
    };
}

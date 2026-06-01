package com.example.demo.service;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.model.Employee;
import com.example.demo.specification.ReactFilterSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<Employee> getAll(int page, int size, String sortField, String sortOrder, String filterJson) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Employee> specification = ReactFilterSpecification.parseSpecificationJson(filterJson);
        return employeeRepository.findAll(specification, pageable);
    }

    public Optional<Employee> getById(int id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getMany(List<Integer> ids) {
        return employeeRepository.findAllById(ids);
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> update(int id, Employee employeeDetails) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(employeeDetails.getName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setSalary(employeeDetails.getSalary());
            employee.setActive(employeeDetails.isActive());
            return employeeRepository.save(employee);
        });
    }

    public void delete(int id) {
        employeeRepository.deleteById(id);
    }

    public void deleteMany(List<Integer> ids) {
        employeeRepository.deleteAllById(ids);
    }
}
package com.example.demo.endpoint.rest.controller;

import com.example.demo.repository.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*", exposedHeaders = "X-Total-Count")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll(
            @RequestParam(value = "_start", defaultValue = "0") int start,
            @RequestParam(value = "_end", defaultValue = "10") int end,
            @RequestParam(value = "_sort", defaultValue = "id") String sort,
            @RequestParam(value = "_order", defaultValue = "ASC") String order,
            @RequestParam(value = "filter", defaultValue = "{}") String filterJson,
            @RequestParam(value = "id", required = false) List<Integer> ids) {

        if (ids != null && !ids.isEmpty()) {
            return ResponseEntity.ok(employeeService.getMany(ids));
        }

        int page = start / (end - start);
        int size = end - start;

        Page<Employee> resultPage = employeeService.getAll(page, size, sort, order, filterJson);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(resultPage.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(resultPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable int id) {
        return employeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.create(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable int id, @RequestBody Employee employee) {
        return employeeService.update(id, employee)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        employeeService.delete(id);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping
    public ResponseEntity<List<Integer>> deleteMany(@RequestParam("filter") String filterJson) {
        return ResponseEntity.ok(List.of());
    }
}
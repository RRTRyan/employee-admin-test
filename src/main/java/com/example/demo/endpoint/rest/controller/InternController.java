package com.example.demo.endpoint.rest.controller;

import com.example.demo.repository.model.Intern;
import com.example.demo.service.InternService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interns")
@CrossOrigin(origins = "*", exposedHeaders = "X-Total-Count")
public class InternController {

    private final InternService internService;

    public InternController(InternService internService) {
        this.internService = internService;
    }

    @GetMapping
    public ResponseEntity<List<Intern>> getAll(
            @RequestParam(value = "_start", defaultValue = "0") int start,
            @RequestParam(value = "_end", defaultValue = "10") int end,
            @RequestParam(value = "_sort", defaultValue = "id") String sort,
            @RequestParam(value = "_order", defaultValue = "ASC") String order,
            @RequestParam(value = "filter", defaultValue = "{}") String filterJson,
            @RequestParam(value = "id", required = false) List<Integer> ids) {

        if (ids != null && !ids.isEmpty()) {
            return ResponseEntity.ok(internService.getMany(ids));
        }

        int page = start / (end - start);
        int size = end - start;

        Page<Intern> resultPage = internService.getAll(page, size, sort, order, filterJson);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(resultPage.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(resultPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intern> getById(@PathVariable int id) {
        return internService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Intern> create(@RequestBody Intern intern) {
        return ResponseEntity.ok(internService.create(intern));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Intern> update(@PathVariable int id, @RequestBody Intern intern) {
        return internService.update(id, intern)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        internService.delete(id);
        return ResponseEntity.ok(Map.of("id", id));
    }
}
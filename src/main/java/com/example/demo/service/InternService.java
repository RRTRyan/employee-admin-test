package com.example.demo.service;

import com.example.demo.repository.InternRepository;
import com.example.demo.repository.model.Intern;
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
public class InternService {

    private final InternRepository internRepository;

    public InternService(InternRepository internRepository) {
        this.internRepository = internRepository;
    }

    public Page<Intern> getAll(int page, int size, String sortField, String sortOrder, String filterJson) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Intern> specification = ReactFilterSpecification.parseSpecificationJson(filterJson);
        return internRepository.findAll(specification, pageable);
    }

    public Optional<Intern> getById(int id) {
        return internRepository.findById(id);
    }

    public List<Intern> getMany(List<Integer> ids) {
        return internRepository.findAllById(ids);
    }

    public Intern create(Intern intern) {
        return internRepository.save(intern);
    }

    public Optional<Intern> update(int id, Intern internDetails) {
        return internRepository.findById(id).map(intern -> {
            intern.setName(internDetails.getName());
            intern.setEmail(internDetails.getEmail());
            intern.setDepartment(internDetails.getDepartment());
            intern.setRemunerated(internDetails.isRemunerated());
            intern.setSalary(internDetails.getSalary());
            intern.setActive(internDetails.isActive());
            intern.setManager(internDetails.getManager());
            return internRepository.save(intern);
        });
    }

    public void delete(int id) {
        internRepository.deleteById(id);
    }

    public void deleteMany(List<Integer> ids) {
        internRepository.deleteAllById(ids);
    }
}
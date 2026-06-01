package com.example.demo.repository;

import com.example.demo.repository.model.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternRepository extends JpaRepository<Intern, Integer> {
    List<Intern> findByManagerId(int managerId);
}

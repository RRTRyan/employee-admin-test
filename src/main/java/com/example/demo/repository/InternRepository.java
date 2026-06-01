package com.example.demo.repository;

import com.example.demo.repository.model.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface InternRepository extends JpaRepository<Intern, Integer>, JpaSpecificationExecutor<Intern> {
}

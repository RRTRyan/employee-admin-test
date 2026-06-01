package com.example.demo.repository.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String name;

  @OneToMany(mappedBy = "manager", fetch =  FetchType.LAZY)
  private List<Intern> interns;

  @Column
  private String email;

  @Column
  private String department;

  @Column
  private Float salary;

  @Column
  private boolean isActive;
}

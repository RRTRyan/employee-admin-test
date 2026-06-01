package com.example.demo.repository.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "interns")
public class Intern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "managerId",
            nullable = false,
            referencedColumnName = "id")
    private Employee manager;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String department;

    @Column
    private boolean isRemunerated;

    @Column
    private Float salary;

    @Column
    private boolean isActive;
}

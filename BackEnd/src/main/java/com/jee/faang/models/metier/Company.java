package com.jee.faang.models.metier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private LocalDate openDate;
    private LocalDate closeDate;
    @OneToMany
    private List<KeyWord> keywords = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee owner;

    public Company(String nom, String description,/* LocalDate openDate, LocalDate closeDate,*/ Employee owner) {
        this.nom = nom;
        this.description = description;
//        this.openDate = openDate;
//        this.closeDate = closeDate;
        this.owner = owner;
    }

    public Company(Long id, String nom, String description, LocalDate openDate, LocalDate closeDate) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }
}

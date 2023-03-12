package fr.louisetom.profilsearch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Offre")
@Getter
@Setter
@NoArgsConstructor

public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date creation_date;
    private String description;
    private String type;
    private String place;
    private int salary;

    @ManyToMany
    @JoinTable(
            name = "question_offre",
            joinColumns = @JoinColumn(name = "offre_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions = new HashSet<>();

    public Offre(String name, Date creation_date, String description, String type, String place, int salary, Set<Question> questions) {
        this.name = name;
        this.creation_date = creation_date;
        this.description = description;
        this.type = type;
        this.place = place;
        this.salary = salary;
        this.questions = questions;
    }

}

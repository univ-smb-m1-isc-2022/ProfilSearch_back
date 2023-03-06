package fr.louisetom.profilsearch.model;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Candidature")
@Getter
@Setter
@NoArgsConstructor

public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String fname;
    private String email;
    private String cv;
    private long id_offre;

    public Candidature(String name, String fname, String email, String cv, long id_offre) {
        this.name = name;
        this.fname = fname;
        this.email = email;
        this.cv = cv;
        this.id_offre = id_offre;
    }

}

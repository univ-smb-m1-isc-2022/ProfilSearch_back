package fr.louisetom.profilsearch.model;

import fr.louisetom.profilsearch.repository.OffreRepository;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "id_offre")
    private Offre offre;

    @OneToMany(mappedBy = "candidature")
    private List<Reponse> reponses;



    public Candidature(String name, String fname, String email, String cv, Offre offre) {
        this.name = name;
        this.fname = fname;
        this.email = email;
        this.cv = cv;
        this.offre = offre;
    }

}

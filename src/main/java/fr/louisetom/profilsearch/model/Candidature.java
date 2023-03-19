package fr.louisetom.profilsearch.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Candidature")
@Getter
@Setter

public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String fname;
    private String email;
    private String cv;

    private String token;

    @ManyToOne
    @JoinColumn(name = "id_offre")
    private fr.louisetom.profilsearch.model.Offre offre;

    @OneToMany(mappedBy = "candidature")
    @JsonManagedReference
    private List<fr.louisetom.profilsearch.model.Reponse> reponses;


    public Candidature() {
        generateToken();
    }

    @PrePersist
    public void generateToken() {
        this.token = java.util.UUID.randomUUID().toString();
        System.out.println("token generate : " + this.token);
    }

}

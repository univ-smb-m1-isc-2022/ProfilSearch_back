package fr.louisetom.profilsearch.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Reponse")
@Getter
@Setter
@NoArgsConstructor
public class Reponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String reponse;

    @ManyToOne
    @JoinColumn(name = "question_id")
    public Question question;

    @ManyToOne
    @JoinColumn(name = "candidature_id")
    @JsonBackReference
    public Candidature candidature;

    public Reponse(String reponse, Question question) {
        this.reponse = reponse;
        this.question = question;
    }

}

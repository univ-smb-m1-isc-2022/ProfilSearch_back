package fr.louisetom.profilsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.louisetom.profilsearch.mail.MailService;
import fr.louisetom.profilsearch.model.Candidature;
import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.model.Question;
import fr.louisetom.profilsearch.model.Reponse;
import fr.louisetom.profilsearch.service.CandidatureService;
import fr.louisetom.profilsearch.service.OffreService;
import fr.louisetom.profilsearch.service.QuestionService;
import fr.louisetom.profilsearch.service.ReponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CandidatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CandidatureService candidatureService;
    @Autowired
    private OffreService offreService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ReponseService reponseService;
    @Autowired
    private MailService mailService;

    @BeforeEach
    public void setup() {
        candidatureService = mock(CandidatureService.class);
        offreService = mock(OffreService.class);
        questionService = mock(QuestionService.class);
        reponseService = mock(ReponseService.class);
        mailService = mock(MailService.class);

        mockMvc = standaloneSetup(new CandidatureController(candidatureService, reponseService, questionService, null, mailService)).build();
    }

    @Test
    public void shouldCreateCandidature() throws Exception {
        Question question1 = new Question("Vos qualités ?");
        question1.setId(3L);
        Question question2 = new Question("Pourquoi voulez-vous travailler avec nous ?");
        question2.setId(4L);
        Set<Question> questions = new HashSet<>(Arrays.asList(question1, question2));

        // Créer un objet Offre à utiliser pour la candidature
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, questions);
        offre.setId(4L);

        // Créer un objet Candidature à partir des données que vous avez fournies
        Candidature candidature = new Candidature("Doe", "John", "john@doe.com", offre);
        candidature.setId(5L);
        List<Reponse> reponses = new ArrayList<>();
        reponses.add(new Reponse("Réponse à la question 1", question1, candidature));
        reponses.add(new Reponse("Réponse à la question 2", question2, candidature));
        candidature.setReponses(reponses);

        // Convertir la candidature en JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String candidatureJson = objectMapper.writeValueAsString(candidature);

        // Configurer les mocks pour les services requis
        when(candidatureService.createCandidature(any(Candidature.class))).thenReturn(candidature);
        when(reponseService.createReponse(any(Reponse.class))).thenReturn(new Reponse());

        // Effectuer une demande POST pour créer la candidature
        mockMvc.perform(MockMvcRequestBuilders.post("/profilsearch/candidature/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidatureJson.getBytes()))
                .andExpect(status().isOk());

        // Vérifier que les services requis ont été appelés une fois
        verify(candidatureService, times(1)).createCandidature(any(Candidature.class));
        verify(reponseService, times(2)).createReponse(any(Reponse.class));
    }

}

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
import static org.hamcrest.Matchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.louisetom.profilsearch.controller.OffreController;
import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.service.OffreService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

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

    @Test
    public void shouldReturnAllCandidatures() throws Exception {
        Question question1 = new Question("Vos qualités ?");
        question1.setId(3L);
        Question question2 = new Question("Pourquoi voulez-vous travailler avec nous ?");
        question2.setId(4L);
        Set<Question> questions = new HashSet<>(Arrays.asList(question1, question2));

        // Créer un objet Offre à utiliser pour la candidature
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, questions);
        offre.setId(4L);

        // Créer un objet Candidature à partir des données que vous avez fournies
        Candidature candidature1 = new Candidature("Doe", "John", "john@doe.com", offre);
        candidature1.setId(5L);
        List<Reponse> reponses = new ArrayList<>();
        reponses.add(new Reponse("Réponse à la question 1", question1, candidature1));
        reponses.add(new Reponse("Réponse à la question 2", question2, candidature1));
        candidature1.setReponses(reponses);

        Candidature candidature2 = new Candidature("Daffy", "Duck", "daffy@duck.com", offre);
        candidature2.setId(6L);
        reponses = new ArrayList<>();
        reponses.add(new Reponse("Réponse à la question 1", question1, candidature2));
        reponses.add(new Reponse("Réponse à la question 2", question2, candidature2));
        candidature2.setReponses(reponses);

        when(candidatureService.getAllCandidature()).thenReturn(List.of(candidature1, candidature2));

        mockMvc.perform(get("/profilsearch/candidature/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Doe")))
                .andExpect(jsonPath("$[0].fname", is("John")))
                .andExpect(jsonPath("$[0].email", is("john@doe.com")))
                .andExpect(jsonPath("$[1].name", is("Daffy")))
                .andExpect(jsonPath("$[1].fname", is("Duck")))
                .andExpect(jsonPath("$[1].email", is("daffy@duck.com")));
    }

    @Test
    public void shouldReturnCandidatureById() throws Exception {
        Question question1 = new Question("Vos qualités ?");
        question1.setId(3L);
        Question question2 = new Question("Pourquoi voulez-vous travailler avec nous ?");
        question2.setId(4L);
        Set<Question> questions = new HashSet<>(Arrays.asList(question1, question2));

        // Créer un objet Offre à utiliser pour la candidature
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, questions);
        offre.setId(4L);

        // Créer un objet Candidature à partir des données que vous avez fournies
        Candidature candidature1 = new Candidature("Doe", "John", "john@doe.com", offre);
        candidature1.setId(5L);
        List<Reponse> reponses = new ArrayList<>();
        reponses.add(new Reponse("Réponse à la question 1", question1, candidature1));
        reponses.add(new Reponse("Réponse à la question 2", question2, candidature1));
        candidature1.setReponses(reponses);

        when(candidatureService.getCandidatureById(anyLong())).thenReturn(candidature1);

        mockMvc.perform(get("/profilsearch/candidature/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Doe")));
    }

    @Test
    public void shouldReturnCandidatureByOffreId() throws Exception {
        Question question1 = new Question("Vos qualités ?");
        question1.setId(3L);
        Question question2 = new Question("Pourquoi voulez-vous travailler avec nous ?");
        question2.setId(4L);
        Set<Question> questions = new HashSet<>(Arrays.asList(question1, question2));

        // Créer un objet Offre à utiliser pour la candidature
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, questions);
        offre.setId(4L);

        // Créer un objet Candidature à partir des données que vous avez fournies
        Candidature candidature1 = new Candidature("Doe", "John", "john@doe.com", offre);
        candidature1.setId(5L);
        List<Reponse> reponses = new ArrayList<>();
        reponses.add(new Reponse("Réponse à la question 1", question1, candidature1));
        reponses.add(new Reponse("Réponse à la question 2", question2, candidature1));
        candidature1.setReponses(reponses);

        Candidature candidature2 = new Candidature("Daffy", "Duck", "daffy@duck.com", offre);
        candidature2.setId(6L);
        reponses = new ArrayList<>();
        reponses.add(new Reponse("Réponse à la question 1", question1, candidature2));
        reponses.add(new Reponse("Réponse à la question 2", question2, candidature2));
        candidature2.setReponses(reponses);


        when(candidatureService.getCandidaturesByOffre(anyLong())).thenReturn(List.of(candidature1, candidature2));

        mockMvc.perform(get("/profilsearch/candidature/offre/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Doe")))
                .andExpect(jsonPath("$[0].fname", is("John")))
                .andExpect(jsonPath("$[0].email", is("john@doe.com")))
                .andExpect(jsonPath("$[1].name", is("Daffy")))
                .andExpect(jsonPath("$[1].fname", is("Duck")))
                .andExpect(jsonPath("$[1].email", is("daffy@duck.com")));
    }

    @Test
    public void shouldDeleteCandidatureByToken () throws Exception {
        Question question1 = new Question("Vos qualités ?");
        question1.setId(3L);
        Question question2 = new Question("Pourquoi voulez-vous travailler avec nous ?");
        question2.setId(4L);
        Set<Question> questions = new HashSet<>(Arrays.asList(question1, question2));

        // Créer un objet Offre à utiliser pour la candidature
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, questions);
        offre.setId(4L);

        // Créer un objet Candidature à partir des données que vous avez fournies
        Candidature candidature1 = new Candidature("Doe", "John", "john@doe.com", offre);
        candidature1.setId(5L);
        List<Reponse> reponses = new ArrayList<>();
        reponses.add(new Reponse("Réponse à la question 1", question1, candidature1));
        reponses.add(new Reponse("Réponse à la question 2", question2, candidature1));
        candidature1.setReponses(reponses);

        when(candidatureService.getCandidatureByToken(anyString())).thenReturn(candidature1);

        mockMvc.perform(get("/profilsearch/candidature/token/123456789"))
                .andExpect(status().isOk());
    }
}

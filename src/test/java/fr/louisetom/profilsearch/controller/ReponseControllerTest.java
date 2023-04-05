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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ReponseControllerTest {

    private MockMvc mockMvc;
    private CandidatureService candidatureService;
    private ReponseService reponseService;

    @BeforeEach
    public void setup(){
        reponseService = mock(ReponseService.class);
        mockMvc = standaloneSetup(new ReponseController(reponseService)).build();
        candidatureService = mock(CandidatureService.class);
    }

    @Test
    public void testCreateReponse() throws Exception {
        // String reponse, Question question, Candidature candidature

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

        // Créer un objet Reponse à partir des données que vous avez fournies
        Reponse reponse = new Reponse("Je suis motivé", question1, candidature);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(reponse);

        // Configurer les mocks pour les services requis
        when(candidatureService.createCandidature(any(Candidature.class))).thenReturn(candidature);
        when(reponseService.createReponse(any(Reponse.class))).thenReturn(new Reponse());

        // Exécuter la requête
        mockMvc.perform(MockMvcRequestBuilders.post("/profilsearch/reponse/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getBytes()))
                .andExpect(status().isOk());

        verify(reponseService, times(1)).createReponse(any(Reponse.class));
    }

    @Test
    public void testGetAllResponse() throws Exception {
        Question question1 = new Question("Vos qualités ?");
        question1.setId(3L);
        Question question2 = new Question("Pourquoi voulez-vous travailler avec nous ?");
        question2.setId(4L);
        Set<Question> questions = new HashSet<>(Arrays.asList(question1, question2));
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, questions);
        offre.setId(4L);
        Candidature candidature = new Candidature("Doe", "John", "john@doe.com", offre);
        candidature.setId(5L);
        Reponse reponse = new Reponse("Je travaille depuis 2 ans en Java", question1, candidature);
        Reponse reponse2 = new Reponse("Je suis motivé", question2, candidature);


        // Configurer les mocks pour les services requis
        when(candidatureService.createCandidature(any(Candidature.class))).thenReturn(candidature);
        when(reponseService.getAllReponse()).thenReturn(List.of(reponse, reponse2));

        // Exécuter la requête
        mockMvc.perform(get("/profilsearch/reponse/getAll"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reponse", is("Je travaille depuis 2 ans en Java")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].reponse", is("Je suis motivé")));
        verify(reponseService, times(1)).getAllReponse();
    }

    @Test
    public void testGetReponseById() throws Exception {
        Question question1 = new Question("Vos qualités ?");
        question1.setId(3L);
        Question question2 = new Question("Pourquoi voulez-vous travailler avec nous ?");
        question2.setId(4L);
        Set<Question> questions = new HashSet<>(Arrays.asList(question1, question2));
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, questions);
        offre.setId(4L);
        Candidature candidature = new Candidature("Doe", "John", "john@doe.com", offre);
        candidature.setId(5L);
        Reponse reponse = new Reponse("Je travaille depuis 2 ans en Java", question1, candidature);
        // Configurer les mocks pour les services requis
        when(candidatureService.createCandidature(any(Candidature.class))).thenReturn(candidature);
        when(reponseService.getReponseById(anyLong())).thenReturn(Optional.of(reponse));

        // Exécuter la requête
        mockMvc.perform(get("/profilsearch/reponse/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reponse", is("Je travaille depuis 2 ans en Java")));

        verify(reponseService, times(1)).getReponseById(anyLong());
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        when(reponseService.getReponseById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/profilsearch/reponse/3"))
                .andExpect(status().isNotFound());
    }
}

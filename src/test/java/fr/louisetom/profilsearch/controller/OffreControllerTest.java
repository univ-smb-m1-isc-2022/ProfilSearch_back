package fr.louisetom.profilsearch.controller;

import static org.hamcrest.Matchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.louisetom.profilsearch.controller.OffreController;
import fr.louisetom.profilsearch.model.Offre;
import fr.louisetom.profilsearch.service.OffreService;

import java.util.Date;


@SpringBootTest
@AutoConfigureMockMvc
public class OffreControllerTest {

    @Autowired
    public MockMvc mockMvc;



    @Test
    public void testCreateOffre() throws Exception {
        // Créer un objet Offre
        Offre offre = new Offre("Dev Web", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDI", "Paris", 3000, null);

        // Convertir l'objet Offre en JSON
        String offreJson = new ObjectMapper().writeValueAsString(offre);

        // Envoyer une requête HTTP POST pour créer une nouvelle offre
        mockMvc.perform(post("/profilsearch/offre/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offreJson.getBytes()))

                // Vérifier que la requête a retourné un code HTTP 200
                .andExpect(status().isOk())

                // Vérifier que l'objet Offre créé a les mêmes attributs que ceux spécifiés dans la méthode createOffreTest()
                .andExpect(jsonPath("$.name").value("Dev Web"))
                .andExpect(jsonPath("$.description").value("Lorem Ipsum is simply dummy text of the printing and typesetting industry."))
                .andExpect(jsonPath("$.type").value("CDI"))
                .andExpect(jsonPath("$.place").value("Paris"))
                .andExpect(jsonPath("$.salary").value(3000));
    }

    @Test
    public void testGetAllOffre() throws Exception {
        mockMvc.perform(get("/profilsearch/offre/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOffreById() throws Exception {
        // Crée une offre
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Convertir l'objet Offre en JSON
        String offreJson = new ObjectMapper().writeValueAsString(offre);

        // Envoyer une requête HTTP POST pour créer une nouvelle offre
        MvcResult result = mockMvc.perform(post("/profilsearch/offre/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offreJson.getBytes()))

                // Vérifier que la requête a retourné un code HTTP 200
                .andExpect(status().isOk())
                .andReturn();

        // Récupérer l'objet Offre créé dans la base de données
        ObjectMapper mapper = new ObjectMapper();
        Offre createdOffre = mapper.readValue(result.getResponse().getContentAsString(), Offre.class);

        // Vérifier que l'objet Offre créé a un identifiant non-null
        assertNotNull(createdOffre.getId());
        System.out.println("Offre ID: " + createdOffre.getId());

        // On récupère l'offre et on regarde si on obtiens bien les memes informations
        mockMvc.perform(get("/profilsearch/offre/" + createdOffre.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dev Java"))
                .andExpect(jsonPath("$.description").value("Lorem Ipsum is simply dummy text of the printing and typesetting industry."))
                .andExpect(jsonPath("$.type").value("CDD"))
                .andExpect(jsonPath("$.place").value("Lyon"))
                .andExpect(jsonPath("$.salary").value(3999));

    }
}

package fr.louisetom.profilsearch.controller;

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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class OffreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OffreService offreService;
    @BeforeEach
    public void setUp() {
        offreService = mock(OffreService.class);
        mockMvc = standaloneSetup(new OffreController(offreService)).build();
    }

    @Test
    public void testCreateOffre() throws Exception {
        /*
        //Creation de l'offre
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        String offreJson = new ObjectMapper().writeValueAsString(offre);

        //Mock de la methode createOffre
        when(offreService.createOffre(any(Offre.class))).thenReturn(offre);

        //Test de la methode createOffre avec l'offre
        mockMvc.perform(post("/profilsearch/offre/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offreJson.getBytes()))
                        .andExpect(status().isOk());

        // Verification que la methode createOffre a bien ete appelee 1 seule fois
         verify(offreService, times(1)).createOffre(any(Offre.class));

         */
    }

    /*
    @Test
    public void testGetAllOffre() throws Exception {
        // Creation de 3 offres
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        Offre offre2 = new Offre("Dev Python", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);
        Offre offre3 = new Offre("Dev C++", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Mock de la methode getAllOffre
        when(offreService.getAllOffre()).thenReturn(List.of(offre, offre2, offre3));

        // Test de la methode getAllOffre qui doit retourner les 3 offres
        mockMvc.perform(get("/profilsearch/offre/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Dev Java")))
                .andExpect(jsonPath("$[1].name", is("Dev Python")))
                .andExpect(jsonPath("$[2].name", is("Dev C++")));

        // Verification que la methode getAllOffre a bien ete appelee 1 seule fois
        verify(offreService, times(1)).getAllOffre();
    }

    @Test
    public void testGetOffreById() throws Exception {
        // Creation d'une offre
        Offre offre = new Offre("Dev Java", new Date(), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "CDD", "Lyon", 3999, null);

        // Mock de la methode getOffreById
        when(offreService.getOffreById(anyLong())).thenReturn(offre);

        // Test de la methode getOffreById qui doit retourner l'offre
        mockMvc.perform(get("/profilsearch/offre/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dev Java")));

        // Verification que la methode getOffreById a bien ete appelee 1 seule fois
        verify(offreService, times(1)).getOffreById(anyLong());
    }

     */

}

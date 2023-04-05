package fr.louisetom.profilsearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.louisetom.profilsearch.mail.MailService;
import fr.louisetom.profilsearch.model.Invitation;
import fr.louisetom.profilsearch.service.InvitationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class InvitationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private MailService mailService;

    @BeforeEach
    public void setup(){
        invitationService = mock(InvitationService.class);
        mailService = mock(MailService.class);
        mockMvc = standaloneSetup(new InvitationController(invitationService, mailService)).build();
    }

    @Test
    public void shouldCreateInvitation() throws Exception {
        Invitation invitation = new Invitation();
        invitation.setEmail("test@test.com");

        when(invitationService.createInvitation(any(Invitation.class))).thenReturn(invitation);

        mockMvc.perform(MockMvcRequestBuilders.post("/profilsearch/invitation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\"}"))
                .andExpect(status().isOk());

        verify(invitationService, times(1)).createInvitation(any(Invitation.class));
        verify(mailService, times(1)).sendMailInvitation(any(Invitation.class));
    }

    @Test
    public void shouldReturnAllInvitations() throws Exception {
        Invitation invitation1 = new Invitation();
        invitation1.setEmail("test1@test.com");
        Invitation invitation2 = new Invitation();
        invitation2.setEmail("test2@test.com");

        List<Invitation> invitations = Arrays.asList(invitation1, invitation2);

        when(invitationService.getAllInvitation()).thenReturn(invitations);

        mockMvc.perform(get("/profilsearch/invitation/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("test1@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("test2@test.com")));
    }

    @Test
    public void shouldReturnInvitationByEmail() throws Exception {
        Invitation invitation = new Invitation();
        invitation.setEmail("test@test.com");

        when(invitationService.getInvitationByEmail(anyString())).thenReturn(Optional.of(invitation));

        ObjectMapper objectMapper = new ObjectMapper();
        String invitationJson = objectMapper.writeValueAsString(invitation);

        mockMvc.perform(get("/profilsearch/invitation/{email}", "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.com")));

    }

    /*
    @Test
    public void shouldReturnInvitationByEmailNotFound() throws Exception {
        when(invitationService.getInvitationByEmail(anyString())).thenReturn(null);

        mockMvc.perform(get("/profilsearch/invitation/test"))
                .andExpect(status().isNotFound());
    }

     */

}


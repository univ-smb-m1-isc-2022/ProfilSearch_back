package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.mail.MailService;
import fr.louisetom.profilsearch.model.Invitation;
import fr.louisetom.profilsearch.repository.InvitationRepository;
import fr.louisetom.profilsearch.service.InvitationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/profilsearch/invitation")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://profilsearch.oups.net"})
public class InvitationController {

    private final InvitationService invitationService;
    private final MailService mailService;

    @PostMapping("/create")
    public Invitation createInvitation(@RequestBody Invitation invitation) throws MessagingException, IOException {
        Invitation invit = invitationService.createInvitation(invitation);
        mailService.sendMailInvitation(invit);
        return invit;
    }

    @GetMapping("/all")
    public List<Invitation> getAllInvitation() {
        return invitationService.getAllInvitation();
    }

    @GetMapping("/{email}")
    public Invitation getInvitationById(@PathVariable String email) {
        return invitationService.getInvitationByEmail(email);
    }

}
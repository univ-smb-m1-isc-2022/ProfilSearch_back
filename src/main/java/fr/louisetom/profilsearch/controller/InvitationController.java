package fr.louisetom.profilsearch.controller;

import fr.louisetom.profilsearch.model.Invitation;
import fr.louisetom.profilsearch.repository.InvitationRepository;
import fr.louisetom.profilsearch.service.InvitationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profilsearch/invitation")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping("/create")
    public Invitation createInvitation(@RequestBody Invitation invitation) {
        return invitationService.createInvitation(invitation);
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
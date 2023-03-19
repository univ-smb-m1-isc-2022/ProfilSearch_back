package fr.louisetom.profilsearch.service;


import fr.louisetom.profilsearch.model.Invitation;

import java.util.List;

public interface InvitationService {

    Invitation createInvitation(Invitation invitation);
    List<Invitation> getAllInvitation();
    Invitation getInvitationByEmail(String string);

    Invitation getInvitationById(Long id);
}

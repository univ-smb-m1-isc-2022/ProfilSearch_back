package fr.louisetom.profilsearch.repository;

import fr.louisetom.profilsearch.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    public Optional<Invitation> findByEmail(String email);
}

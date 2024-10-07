package kz.pavershin.repository;

import kz.pavershin.model.Tournament;
import kz.pavershin.model.TournamentParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentParticipantRepository extends JpaRepository<TournamentParticipant, Long>{

    List<TournamentParticipant> findByTournamentId(Long tournamentId);
}


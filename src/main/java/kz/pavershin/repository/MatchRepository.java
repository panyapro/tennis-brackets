package kz.pavershin.repository;

import kz.pavershin.model.Match;
import kz.pavershin.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository  extends JpaRepository<Match, Long> {

    List<Match> findByTournamentId(Long tournamentId);
}

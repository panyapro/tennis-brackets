package kz.pavershin.service;

import kz.pavershin.dto.request.CreateTournamentRequestDTO;
import kz.pavershin.dto.response.TournamentResponseDTO;
import kz.pavershin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.pavershin.dto.*;
import kz.pavershin.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private TournamentParticipantRepository tournamentParticipantRepository;

    @Autowired
    private PlacementRepository placementRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ParticipantLossRepository participantLossRepository;

    @Transactional
    public TournamentResponseDTO createTournament(CreateTournamentRequestDTO createTournamentDTO) {
        Tournament tournament = createAndSaveTournament(createTournamentDTO);
        List<TournamentParticipant> tournamentParticipants = createAndSaveParticipants(createTournamentDTO, tournament);
        List<Placement> placements = createAndSavePlacements(tournament);
        List<Match> initialMatches = createAndSaveInitialMatches(tournament, tournamentParticipants);

        return buildTournamentResponseDTO(tournament, tournamentParticipants, placements, initialMatches);
    }

    private Tournament createAndSaveTournament(CreateTournamentRequestDTO createTournamentDTO) {
        Tournament tournament = new Tournament();
        tournament.setName(createTournamentDTO.getName());
        tournament.setStartDate(createTournamentDTO.getStartDate());
        tournament.setEndDate(createTournamentDTO.getEndDate());
        tournamentRepository.save(tournament);
        return tournament;
    }

    private List<TournamentParticipant> createAndSaveParticipants(CreateTournamentRequestDTO createTournamentDTO, Tournament tournament) {
        List<TournamentParticipant> tournamentParticipants = new ArrayList<>();
        for (ParticipantDTO participantDTO : createTournamentDTO.getParticipants()) {
            Participant participant = participantRepository.findByName(participantDTO.getName())
                    .orElseGet(() -> {
                        Participant newParticipant = new Participant();
                        newParticipant.setName(participantDTO.getName());
                        participantRepository.save(newParticipant);
                        return newParticipant;
                    });

            TournamentParticipant tp = new TournamentParticipant();
            tp.setTournament(tournament);
            tp.setParticipant(participant);
            tp.setSeeding(participantDTO.getSeeding());
            tournamentParticipantRepository.save(tp);
            tournamentParticipants.add(tp);
        }
        tournamentParticipants.sort(Comparator.comparingInt(TournamentParticipant::getSeeding));
        return tournamentParticipants;
    }

    private List<Placement> createAndSavePlacements(Tournament tournament) {
        List<Placement> placements = createPlacements(tournament);
        placementRepository.saveAll(placements);
        return placements;
    }

    private List<Match> createAndSaveInitialMatches(Tournament tournament, List<TournamentParticipant> participants) {
        List<Match> initialMatches = createInitialMatches(tournament, participants);
        matchRepository.saveAll(initialMatches);
        return initialMatches;
    }

    private TournamentResponseDTO buildTournamentResponseDTO(Tournament tournament, List<TournamentParticipant> participants, List<Placement> placements, List<Match> matches) {
        TournamentResponseDTO responseDTO = new TournamentResponseDTO();
        responseDTO.setId(tournament.getId());
        responseDTO.setName(tournament.getName());
        responseDTO.setStartDate(tournament.getStartDate());
        responseDTO.setEndDate(tournament.getEndDate());
        responseDTO.setParticipants(convertParticipantsToDTO(participants));
        responseDTO.setPlacements(convertPlacementsToDTO(placements));
        responseDTO.setMatches(convertMatchesToDTO(matches));
        return responseDTO;
    }

    private List<ParticipantDTO> convertParticipantsToDTO(List<TournamentParticipant> participants) {
        return participants.stream()
                .map(tp -> {
                    ParticipantDTO dto = new ParticipantDTO();
                    dto.setId(tp.getParticipant().getId());
                    dto.setName(tp.getParticipant().getName());
                    dto.setSeeding(tp.getSeeding());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<PlacementDTO> convertPlacementsToDTO(List<Placement> placements) {
        return placements.stream()
                .map(p -> {
                    PlacementDTO dto = new PlacementDTO();
                    dto.setId(p.getId());
                    dto.setPlacement(p.getPlacement());
                    dto.setDescription(p.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<MatchDTO> convertMatchesToDTO(List<Match> matches) {
        return matches.stream()
                .map(this::convertMatchToDTO)
                .collect(Collectors.toList());
    }

    private List<Placement> createPlacements(Tournament tournament) {
        List<Placement> placements = new ArrayList<>();
        placements.add(new Placement(tournament, 17, "17-32 место"));
        placements.add(new Placement(tournament, 9, "9-16 место"));
        placements.add(new Placement(tournament, 5, "5-8 место"));
        placements.add(new Placement(tournament, 3, "3-4 место"));
        placements.add(new Placement(tournament, 2, "2 место"));
        return placements;
    }

    private List<Match> createInitialMatches(Tournament tournament, List<TournamentParticipant> participants) {
        List<Match> matches = new ArrayList<>();
        Map<Integer, TournamentParticipant> seedingMap = participants.stream()
                .collect(Collectors.toMap(TournamentParticipant::getSeeding, tp -> tp));

        int[][] matchups = {
                {1, 32}, {16, 17}, {8, 25}, {9, 24}, {4, 29}, {13, 20}, {5, 28}, {12, 21},
                {2, 31}, {15, 18}, {7, 26}, {10, 23}, {3, 30}, {14, 19}, {6, 27}, {11, 22}
        };

        int matchNumber = 1;
        for (int[] matchup : matchups) {
            TournamentParticipant player1 = seedingMap.get(matchup[0]);
            TournamentParticipant player2 = seedingMap.get(matchup[1]);

            Match match = new Match();
            match.setTournament(tournament);
            match.setBracketType(BracketType.WINNERS);
            match.setPlacement(null);
            match.setRound(1);
            match.setMatchNumber(matchNumber++);
            match.setPlayer1(player1);
            match.setPlayer2(player2);
            match.setScheduledTime(tournament.getStartDate().with(LocalTime.of(10, 0)));
            match.setCompletedTime(null);

            matches.add(match);
        }

        return matches;
    }

    private MatchDTO convertMatchToDTO(Match match) {
        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setBracketType(match.getBracketType());
        dto.setRound(match.getRound());
        dto.setMatchNumber(match.getMatchNumber());

        if (match.getPlayer1() != null) {
            ParticipantDTO p1 = new ParticipantDTO();
            p1.setId(match.getPlayer1().getParticipant().getId());
            p1.setName(match.getPlayer1().getParticipant().getName());
            p1.setSeeding(match.getPlayer1().getSeeding());
            dto.setPlayer1(p1);
        }

        if (match.getPlayer2() != null) {
            ParticipantDTO p2 = new ParticipantDTO();
            p2.setId(match.getPlayer2().getParticipant().getId());
            p2.setName(match.getPlayer2().getParticipant().getName());
            p2.setSeeding(match.getPlayer2().getSeeding());
            dto.setPlayer2(p2);
        }

        if (match.getWinner() != null) {
            ParticipantDTO winner = new ParticipantDTO();
            winner.setId(match.getWinner().getParticipant().getId());
            winner.setName(match.getWinner().getParticipant().getName());
            winner.setSeeding(match.getWinner().getSeeding());
            dto.setWinner(winner);
        }

        if (match.getLoser() != null) {
            ParticipantDTO loser = new ParticipantDTO();
            loser.setId(match.getLoser().getParticipant().getId());
            loser.setName(match.getLoser().getParticipant().getName());
            loser.setSeeding(match.getLoser().getSeeding());
            dto.setLoser(loser);
        }

        dto.setPreviousMatch1Id(match.getPreviousMatch1() != null ? match.getPreviousMatch1().getId() : null);
        dto.setPreviousMatch2Id(match.getPreviousMatch2() != null ? match.getPreviousMatch2().getId() : null);
        dto.setScheduledTime(match.getScheduledTime());
        dto.setCompletedTime(match.getCompletedTime());
        dto.setPlacementId(match.getPlacement() != null ? match.getPlacement().getId() : null);

        return dto;
    }

    public TournamentResponseDTO getCompletedTournamentBracket(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Турнир не найден"));

        if (tournament.getEndDate() == null || tournament.getEndDate().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Турнир не завершён");
        }

        List<TournamentParticipant> tournamentParticipants = tournamentParticipantRepository.findByTournamentId(tournamentId);
        List<ParticipantDTO> participants = convertParticipantsToDTO(tournamentParticipants);

        List<Placement> placements = placementRepository.findByTournamentId(tournamentId);
        List<PlacementDTO> placementDTOs = convertPlacementsToDTO(placements);

        List<Match> matches = matchRepository.findByTournamentId(tournamentId);
        List<MatchDTO> matchDTOs = convertMatchesToDTO(matches);

        return buildTournamentResponseDTO(tournament, tournamentParticipants, placements, matches);
    }
}
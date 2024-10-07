package kz.pavershin.controller;

import kz.pavershin.dto.request.CreateTournamentRequestDTO;
import kz.pavershin.dto.response.TournamentResponseDTO;
import kz.pavershin.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    /**
     * Создание нового турнира
     *
     * @param createTournamentDTO Данные для создания турнира
     * @return Ответ с информацией о созданном турнире
     */
    @PostMapping
    public ResponseEntity<TournamentResponseDTO> createTournament(@Validated @RequestBody CreateTournamentRequestDTO createTournamentDTO) {
        try {
            TournamentResponseDTO responseDTO = tournamentService.createTournament(createTournamentDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            // Логирование ошибки (опционально)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение информации о турнире по ID
     *
     * @param id Идентификатор турнира
     * @return Ответ с информацией о турнире
     */
    @GetMapping("/{id}")
    public ResponseEntity<TournamentResponseDTO> getTournament(@PathVariable Long id) {
        try {
            TournamentResponseDTO responseDTO = tournamentService.getCompletedTournamentBracket(id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Логирование ошибки (опционально)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}


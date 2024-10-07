package kz.pavershin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kz.pavershin.dto.ParticipantDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateTournamentRequestDTO {

    @NotBlank(message = "Название турнира обязательно")
    private String name;

    @NotNull(message = "Дата начала обязательна")
    private LocalDateTime startDate;

    @NotNull(message = "Дата окончания обязательна")
    private LocalDateTime endDate;

    @NotEmpty(message = "Должно быть как минимум два участника")
    private List<ParticipantDTO> participants;
}

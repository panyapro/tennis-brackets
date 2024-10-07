package kz.pavershin.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tournaments")
public class Tournament extends BaseEntity{

    private String name;

    @Column(columnDefinition = "date",name = "start_date")
    private LocalDateTime startDate;

    @Column(columnDefinition = "date",name = "end_date")
    private LocalDateTime endDate;

    @Column(columnDefinition = "TIMESTAMP",name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP",name = "updated_at")
    private LocalDateTime updatedAt;

    // Связи

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TournamentParticipant> tournamentParticipants;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matches;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Placement> placements;

    // Геттеры и сеттеры

    // Конструкторы, хэш-коды и equals
}

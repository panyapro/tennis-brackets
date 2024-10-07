package kz.pavershin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="tournament_participants")
public class TournamentParticipant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    private Integer seeding;

    @Column(columnDefinition = "TIMESTAMP",name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP",name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "player1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matchesAsPlayer1;

    @OneToMany(mappedBy = "player2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matchesAsPlayer2;

    // Геттеры и сеттеры

    // Конструкторы, хэш-коды и equals
}

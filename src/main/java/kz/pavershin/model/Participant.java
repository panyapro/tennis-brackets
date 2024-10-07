package kz.pavershin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "participants")
public class Participant extends BaseEntity {

    private String name;

    @Column(columnDefinition = "TIMESTAMP",name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP",name = "updated_at")
    private LocalDateTime updatedAt;

    // Связи

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TournamentParticipant> tournamentParticipants;
}

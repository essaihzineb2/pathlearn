package org.example.projet.model.formation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "formation_id" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {

    public enum Statut {
        ACTIVE, ANNULEE, TERMINEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // userId stocké comme simple colonne (User est dans la DB auth_microservice)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @Column(name = "date_inscription", updatable = false)
    private LocalDateTime dateInscription;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Statut statut = Statut.ACTIVE;

    @PrePersist
    protected void onCreate() {
        this.dateInscription = LocalDateTime.now();
        if (this.statut == null) {
            this.statut = Statut.ACTIVE;
        }
    }
}

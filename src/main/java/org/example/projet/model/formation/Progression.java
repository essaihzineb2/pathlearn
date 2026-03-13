package org.example.projet.model.formation;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "progressions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Progression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inscription_id", nullable = false, unique = true)
    private Inscription inscription;

    @Column(nullable = false)
    private Integer pourcentage = 0;

    @Column(name = "derniere_activite")
    private LocalDateTime derniereActivite;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.derniereActivite = LocalDateTime.now();
        if (this.pourcentage == null)
            this.pourcentage = 0;
    }
}

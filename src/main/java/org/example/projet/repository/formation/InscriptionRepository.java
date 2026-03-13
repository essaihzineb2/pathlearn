package org.example.projet.repository.formation;

import org.example.projet.model.formation.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findByUserId(Long userId);

    List<Inscription> findByFormationId(Long formationId);

    boolean existsByUserIdAndFormationId(Long userId, Long formationId);
}

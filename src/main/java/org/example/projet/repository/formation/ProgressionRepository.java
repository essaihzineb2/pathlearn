package org.example.projet.repository.formation;

import org.example.projet.model.formation.Progression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressionRepository extends JpaRepository<Progression, Long> {
    Optional<Progression> findByInscriptionId(Long inscriptionId);
}

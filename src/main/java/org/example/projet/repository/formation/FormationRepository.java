package org.example.projet.repository.formation;

import org.example.projet.model.formation.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
}

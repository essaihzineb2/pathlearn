package org.example.projet.service;

import lombok.RequiredArgsConstructor;
import org.example.projet.dto.ProgressionDTO;
import org.example.projet.exception.ResourceNotFoundException;
import org.example.projet.model.formation.Inscription;
import org.example.projet.model.formation.Progression;
import org.example.projet.repository.formation.InscriptionRepository;
import org.example.projet.repository.formation.ProgressionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional("formationTransactionManager")
public class ProgressionService {

    private final ProgressionRepository progressionRepository;
    private final InscriptionRepository inscriptionRepository;

    public Progression getProgression(Long inscriptionId) {
        return progressionRepository.findByInscriptionId(inscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucune progression trouvée pour l'inscription id : " + inscriptionId));
    }

    public Progression upsertProgression(Long inscriptionId, ProgressionDTO dto) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscription non trouvée avec l'id : " + inscriptionId));

        if (dto.getPourcentage() < 0 || dto.getPourcentage() > 100) {
            throw new RuntimeException("Le pourcentage doit être entre 0 et 100.");
        }

        Progression progression = progressionRepository.findByInscriptionId(inscriptionId)
                .orElse(new Progression());

        progression.setInscription(inscription);
        progression.setPourcentage(dto.getPourcentage());
        progression.setNotes(dto.getNotes());
        return progressionRepository.save(progression);
    }

    public void deleteProgression(Long id) {
        Progression progression = progressionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progression non trouvée avec l'id : " + id));
        progressionRepository.delete(progression);
    }
}

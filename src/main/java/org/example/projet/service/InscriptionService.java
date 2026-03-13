package org.example.projet.service;

import lombok.RequiredArgsConstructor;
import org.example.projet.dto.InscriptionRequestDTO;
import org.example.projet.dto.InscriptionStatutDTO;
import org.example.projet.exception.ResourceNotFoundException;
import org.example.projet.model.formation.Formation;
import org.example.projet.model.formation.Inscription;
import org.example.projet.repository.formation.FormationRepository;
import org.example.projet.repository.formation.InscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional("formationTransactionManager")
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final FormationRepository formationRepository;

    public List<Inscription> getInscriptionsForUser(Long userId) {
        return inscriptionRepository.findByUserId(userId);
    }

    public List<Inscription> getAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    public Inscription inscrire(Long userId, InscriptionRequestDTO dto) {
        if (inscriptionRepository.existsByUserIdAndFormationId(userId, dto.getFormationId())) {
            throw new RuntimeException("Vous êtes déjà inscrit à cette formation.");
        }
        Formation formation = formationRepository.findById(dto.getFormationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Formation non trouvée avec l'id : " + dto.getFormationId()));

        Inscription inscription = new Inscription();
        inscription.setUserId(userId);
        inscription.setFormation(formation);
        return inscriptionRepository.save(inscription);
    }

    public Inscription updateStatut(Long id, InscriptionStatutDTO dto) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscription non trouvée avec l'id : " + id));
        try {
            Inscription.Statut statut = Inscription.Statut.valueOf(dto.getStatut().toUpperCase());
            inscription.setStatut(statut);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut invalide. Valeurs acceptées : ACTIVE, ANNULEE, TERMINEE");
        }
        return inscriptionRepository.save(inscription);
    }

    public void deleteInscription(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscription non trouvée avec l'id : " + id));
        inscriptionRepository.delete(inscription);
    }
}

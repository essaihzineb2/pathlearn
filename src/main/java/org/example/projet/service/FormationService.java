package org.example.projet.service;

import lombok.RequiredArgsConstructor;
import org.example.projet.dto.FormationDTO;
import org.example.projet.exception.ResourceNotFoundException;
import org.example.projet.model.formation.Formation;
import org.example.projet.repository.formation.FormationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional("formationTransactionManager")
public class FormationService {

    private final FormationRepository formationRepository;

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Formation getFormationById(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation non trouvée avec l'id : " + id));
    }

    public Formation createFormation(FormationDTO dto) {
        Formation formation = new Formation();
        formation.setTitre(dto.getTitre());
        formation.setDescription(dto.getDescription());
        formation.setDureeHeures(dto.getDureeHeures());
        return formationRepository.save(formation);
    }

    public Formation updateFormation(Long id, FormationDTO dto) {
        Formation formation = getFormationById(id);
        formation.setTitre(dto.getTitre());
        formation.setDescription(dto.getDescription());
        formation.setDureeHeures(dto.getDureeHeures());
        return formationRepository.save(formation);
    }

    public void deleteFormation(Long id) {
        Formation formation = getFormationById(id);
        formationRepository.delete(formation);
    }
}

package org.example.projet.controller;

import lombok.RequiredArgsConstructor;
import org.example.projet.dto.FormationDTO;
import org.example.projet.model.formation.Formation;
import org.example.projet.service.FormationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
public class FormationController {

    private final FormationService formationService;

    // PUBLIC - Lister toutes les formations
    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        return ResponseEntity.ok(formationService.getAllFormations());
    }

    // PUBLIC - Voir une formation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.getFormationById(id));
    }

    // ADMIN - Créer une formation
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Formation> createFormation(@RequestBody FormationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(formationService.createFormation(dto));
    }

    // ADMIN - Mettre à jour une formation
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody FormationDTO dto) {
        return ResponseEntity.ok(formationService.updateFormation(id, dto));
    }

    // ADMIN - Supprimer une formation
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}

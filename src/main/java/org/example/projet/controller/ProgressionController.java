package org.example.projet.controller;

import lombok.RequiredArgsConstructor;
import org.example.projet.dto.ProgressionDTO;
import org.example.projet.model.formation.Progression;
import org.example.projet.service.ProgressionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progressions")
@RequiredArgsConstructor
public class ProgressionController {

    private final ProgressionService progressionService;

    // USER - Voir la progression pour une inscription
    @GetMapping("/{inscriptionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Progression> getProgression(@PathVariable Long inscriptionId) {
        return ResponseEntity.ok(progressionService.getProgression(inscriptionId));
    }

    // USER - Créer ou mettre à jour la progression (upsert)
    @PostMapping("/{inscriptionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Progression> upsertProgression(@PathVariable Long inscriptionId,
            @RequestBody ProgressionDTO dto) {
        return ResponseEntity.ok(progressionService.upsertProgression(inscriptionId, dto));
    }

    // USER - Supprimer la progression par son propre ID
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteProgression(@PathVariable Long id) {
        progressionService.deleteProgression(id);
        return ResponseEntity.noContent().build();
    }
}

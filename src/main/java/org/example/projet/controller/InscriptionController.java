package org.example.projet.controller;

import lombok.RequiredArgsConstructor;
import org.example.projet.dto.InscriptionRequestDTO;
import org.example.projet.dto.InscriptionStatutDTO;
import org.example.projet.model.formation.Inscription;
import org.example.projet.model.auth.User;
import org.example.projet.service.AuthService;
import org.example.projet.service.InscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final AuthService authService;

    // USER - Voir mes inscriptions (utilisateur connecté)
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Inscription>> getMyInscriptions() {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.ok(inscriptionService.getInscriptionsForUser(currentUser.getId()));
    }

    // ADMIN - Voir toutes les inscriptions
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Inscription>> getAllInscriptions() {
        return ResponseEntity.ok(inscriptionService.getAllInscriptions());
    }

    // USER - S'inscrire à une formation
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Inscription> inscrire(@RequestBody InscriptionRequestDTO dto) {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inscriptionService.inscrire(currentUser.getId(), dto));
    }

    // USER/ADMIN - Changer le statut d'une inscription
    @PutMapping("/{id}/statut")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Inscription> updateStatut(@PathVariable Long id,
            @RequestBody InscriptionStatutDTO dto) {
        return ResponseEntity.ok(inscriptionService.updateStatut(id, dto));
    }

    // USER/ADMIN - Supprimer une inscription
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long id) {
        inscriptionService.deleteInscription(id);
        return ResponseEntity.noContent().build();
    }
}

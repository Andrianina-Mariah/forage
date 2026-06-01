package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DemandeAlerteService;
import com.example.demo.service.DemandeAlerteService.DemandeIntrouvableException;

@RestController
public class DemandeAlerteApiController {
	private final DemandeAlerteService demandeAlerteService;

	public DemandeAlerteApiController(DemandeAlerteService demandeAlerteService) {
		this.demandeAlerteService = demandeAlerteService;
	}

	@GetMapping("/api/demandes/{id}/alertes")
	public ResponseEntity<?> alertes(@PathVariable("id") int idDemande) {
		try {
			return ResponseEntity.ok(demandeAlerteService.calculerAlertes(idDemande));
		} catch (DemandeIntrouvableException exception) {
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(Map.of("erreur", exception.getMessage()));
		}
	}
}

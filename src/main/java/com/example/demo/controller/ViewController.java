package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

	/*@GetMapping("/demandes")
	public String demandes(@RequestParam(name = "status", required = false) String status,
			Model model) {
		model.addAttribute("appTitle", "Forage - Demandes");
		List<StatusView> statuts = statuses();
		List<DemandeView> demandes = demandes();
		if (status != null && !status.isBlank()) {
			String normalized = status.toLowerCase(Locale.ROOT);
			demandes = demandes.stream()
					.filter(demande -> demande.status().toLowerCase(Locale.ROOT).equals(normalized))
					.collect(Collectors.toList());
		}
		model.addAttribute("statuts", statuts);
		model.addAttribute("demandes", demandes);
		model.addAttribute("selectedStatus", status == null ? "" : status);
		return "demandes";
	}*/

	@GetMapping("/statuts")
	public String statuts(Model model) {
		model.addAttribute("appTitle", "Forage - Statuts");
		model.addAttribute("statuts", statuses());
		return "statuts";
	}

	private List<StatusView> statuses() {
		return List.of(
				new StatusView(1, "Nouvelle"),
				new StatusView(2, "Demande de devis d'étude créée"),
				new StatusView(3, "Demande de devis d'étude refusée"),
				new StatusView(4, "Demande de devis de forage créée"),
				new StatusView(5, "Demande de devis de forage refusée")
		);
	}


	public record StatusView(int id, String libelle) {
	}

	public record DemandeView(String reference, String libelle, String commune, String status, LocalDate dateDemande) {
	}
}

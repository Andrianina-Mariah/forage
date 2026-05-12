package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.*;
import com.example.demo.repository.*;

@Controller
public class DemandeController {
	private final CommuneRepository communeRepository;
	private final DemandeRepository demandeRepository;
	private final Demande_statutRepository demande_statutRepository;
	private static final int DEFAULT_STATUT_ID = 1;

	public DemandeController(CommuneRepository communeRepository, DemandeRepository demandeRepository, Demande_statutRepository demande_statutRepository) {
		this.communeRepository = communeRepository;
		this.demandeRepository = demandeRepository;
		this.demande_statutRepository = demande_statutRepository;
	}

	@GetMapping("/demande/new")
	public String demandeForm(Model model) {
		model.addAttribute("appTitle", "Forage - Nouvelle demande");
		model.addAttribute("communes", communeRepository.findAll());
		model.addAttribute("today", LocalDate.now());
		return "demande-form";
	}
	
	@GetMapping("/demande")
	public String demande(Model model) {
		model.addAttribute("appTitle", "Forage - Mes demandes");
		return "demande-list";
	}

	@PostMapping("/demande")
	public String doDemande(@RequestParam("lieu_demande") String lieu,
			@RequestParam("reference") String reference,
			@RequestParam("id_demandeur") int idDemandeur,
			@RequestParam("id_commune") int idCommune,
			@RequestParam("libelle_demande") String libelleDemande,
			@RequestParam(name = "date_demande", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDemande,
			Model model) {
		LocalDate effectiveDate = dateDemande != null ? dateDemande : LocalDate.now();
		Date sqlDate = Date.valueOf(effectiveDate);
		Demande demande = new Demande(sqlDate, 0, idCommune, idDemandeur, libelleDemande, lieu, reference);
		demandeRepository.save(demande);
		Demande_statut demandeStatut = new Demande_statut(sqlDate, null, 0, demande.getId(), DEFAULT_STATUT_ID);
		demande_statutRepository.save(demandeStatut);
		return "redirect:/demande/new";
	}
	
	@GetMapping("/demande/list")
	public String demandeList(Model model) {
		model.addAttribute("appTitle", "Forage - Liste demande");
		model.addAttribute("demandes", demandeRepository.findAll());
		model.addAttribute("today", LocalDate.now());
		return "demandes";
	}
}

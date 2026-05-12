// package com.example.demo.controller;

// import java.time.LocalDate;
// import java.util.Date;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.demo.model.*;
// import com.example.demo.repository.*;

// @Controller
// public class StatutController {
// 	private final StatutRepository statutRepository;

// 	public StatutController(StatutRepository statutRepository) {
// 		this.statutRepository = statutRepository;
// 	}

// 	@GetMapping("/demande/new")
// 	public String demandeForm(Model model) {
// 		model.addAttribute("appTitle", "Forage - Nouvelle demande");
// 		model.addAttribute("communes", communeRepository.findAll());
// 		model.addAttribute("today", LocalDate.now());
// 		return "demande-form";
// 	}
	
// 	@GetMapping("/demande")
// 	public String demande(Model model) {
// 		model.addAttribute("appTitle", "Forage - Mes demandes");
// 		return "demande-list";
// 	}

// 	@PostMapping("/demande")
// 	public String doDemande(@RequestParam("lieu_demande") String lieu,
// 			@RequestParam("reference") String reference,
// 			@RequestParam("id_demandeur") int idDemandeur,
// 			@RequestParam("id_commune") int idCommune,
// 			@RequestParam("libelle_demande") String libelleDemande,
// 			@RequestParam("date_demande") Date date_demande,
// 			Model model) {
// 		Demande demande = new Demande(date_demande, 0, idCommune, idDemandeur, libelleDemande, lieu, reference);
// 		demandeRepository.save(demande);
// 		Demande_statut demande_statut = new Demande_statut(date_demande, NULL, id_demande, id_statut);
// 		Demande_statutRepository.save(demande_statut);
// 		return "redirect:/demande/new";
// 	}
	
// 	@GetMapping("/demande/list")
// 	public String demandeList(Model model) {
// 		model.addAttribute("appTitle", "Forage - Liste demande");
// 		model.addAttribute("demandes", demandeRepository.findAll());
// 		model.addAttribute("today", LocalDate.now());
// 		return "demandes";
// 	}
// }

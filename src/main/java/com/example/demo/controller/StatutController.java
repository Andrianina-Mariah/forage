package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.*;
import com.example.demo.repository.*;

@Controller
public class StatutController {
	private final StatutRepository statutRepository;

	public StatutController(StatutRepository statutRepository) {
		this.statutRepository = statutRepository;
	}

	@GetMapping("/statut")
	public String statutForm(Model model) {
		model.addAttribute("appTitle", "Forage - Nouvelle statut");
		model.addAttribute("statut", statutRepository.findAll());
		return "statuts";
	}
	
	@PostMapping("/statut")
	public String doStatut(@RequestParam("libelle") String libelle,
			Model model) {
		Statut statut = new Statut(0, libelle);
		statutRepository.save(statut);
		return "redirect:/statut";
	}
	
    @PostMapping("/statut/update")
	public String modifStatut(@RequestParam("newLibelle") String libelle,
			Model model) {
		Statut statut = new Statut(0, libelle);
		statutRepository.update(statut);
		return "redirect:/statut";
	}

	@GetMapping("/statut/list")
	public String statutList(Model model) {
		model.addAttribute("appTitle", "Forage - Liste statuts");
		model.addAttribute("statut", statutRepository.findAll());
		return "statuts";
	}
}

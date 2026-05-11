package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.CommuneRepository;

@Controller
public class DemandeController {
	private final CommuneRepository communeRepository;

	public DemandeController(CommuneRepository communeRepository) {
		this.communeRepository = communeRepository;
	}

	@GetMapping("/demande/new")
	public String demandeForm(Model model) {
		model.addAttribute("appTitle", "Forage - Nouvelle demande");
		model.addAttribute("communes", communeRepository.findAll());
		model.addAttribute("today", LocalDate.now());
		return "demande-form";
	}
}

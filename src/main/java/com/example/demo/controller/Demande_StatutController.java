package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Demande_statut;
import com.example.demo.repository.DemandeRepository;
import com.example.demo.repository.Demande_statutRepository;
import com.example.demo.repository.StatutRepository;

@Controller
public class Demande_StatutController {
	private final DemandeRepository demandeRepository;
    private final Demande_statutRepository demande_statutRepository;
    private final StatutRepository statutRepository;

    public Demande_StatutController(DemandeRepository demandeRepository, Demande_statutRepository demande_statutRepository, StatutRepository statutRepository) {
        this.demandeRepository = demandeRepository;
        this.demande_statutRepository = demande_statutRepository;
        this.statutRepository = statutRepository;
    }

    @GetMapping("/demande-statut/new")
    public String demandeStatutForm(Model model) {
        model.addAttribute("appTitle", "Forage - Nouveau demande statut");
        model.addAttribute("demandes", demandeRepository.findAll());
        model.addAttribute("statuts", statutRepository.findAll());
        return "demande-statut-new";
    }

    @PostMapping("/demande-statut")
    public String doDemandeStatut(@RequestParam("id_demande") int idDemande,
        @RequestParam("id_statut") int idStatut,
        @RequestParam(name = "observation", required = false, defaultValue = "") String observation,
        @RequestParam(name = "date_debut", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateDebut,
        Model model) {
        LocalDateTime resolvedDateDebut = (dateDebut != null) ? dateDebut : LocalDateTime.now();
        Date sqlDateDebut = Date.from(resolvedDateDebut.atZone(ZoneId.systemDefault()).toInstant());
        Demande_statut demandeStatut = new Demande_statut(observation, sqlDateDebut, null, 0, idDemande, idStatut);
        demande_statutRepository.save(demandeStatut);
        return "redirect:/demande-statut-new";
    }

    @GetMapping("/demande-statut/list")
    public String demandeStatutList(Model model) {
        model.addAttribute("appTitle", "Forage - Liste des demande statuts");
        model.addAttribute("demandesStatuts", demande_statutRepository.findAll());
        return "demande-statut-list";
    }
}

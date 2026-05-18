package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.*;
import com.example.demo.repository.*;

@Controller
public class DevisController {
    private final DevisRepository devisRepository;
    private final DemandeRepository demandeRepository;
    private final Demande_statutRepository demande_statutRepository;
    private final Devis_detailRepository devis_detailRepository;

    public DevisController(DevisRepository devisRepository, DemandeRepository demandeRepository, Demande_statutRepository demande_statutRepository, Devis_detailRepository devis_detailRepository) {
        this.devisRepository = devisRepository;
        this.demandeRepository = demandeRepository;
        this.demande_statutRepository = demande_statutRepository;
        this.devis_detailRepository = devis_detailRepository;
    }

    @GetMapping("/devis/new")
    public String devisForm(Model model) {
        model.addAttribute("appTitle", "Forage - Nouveau devis");
        model.addAttribute("demandes", demandeRepository.findAll());
        model.addAttribute("today", LocalDate.now());
        return "devis-form";
    }

    @PostMapping("/devis")
    public String doDevis(@RequestParam("id_demande") int idDemande,
        @RequestParam("type") String type,
        @RequestParam(name = "description", required = false, defaultValue = "") String description,
        @RequestParam(name = "date", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(name = "libelle", required = false) List<String> libelles,
        @RequestParam(name = "quantite", required = false) List<Double> quantites,
        @RequestParam(name = "prix_unitaire", required = false) List<Double> prixUnitaires,
        Model model) {
        LocalDate resolvedDate = (date != null) ? date : LocalDate.now();
        Devis devis = new Devis(resolvedDate, description, 0, idDemande, type);
        devisRepository.save(devis);
        if (libelles != null && quantites != null && prixUnitaires != null) {
            int count = Math.min(libelles.size(), Math.min(quantites.size(), prixUnitaires.size()));
            for (int i = 0; i < count; i++) {
                String libelle = libelles.get(i);
                if (libelle == null || libelle.isBlank()) {
                    continue;
                }
                double quantite = quantites.get(i) == null ? 0 : quantites.get(i);
                double prixUnitaire = prixUnitaires.get(i) == null ? 0 : prixUnitaires.get(i);
                Devis_detail detail = new Devis_detail(libelle, quantite, prixUnitaire, devis.getId());
                devis_detailRepository.save(detail);
            }
        }
        demande_statutRepository.closeOpenStatut(idDemande, resolvedDate);
        int statutId = "realisation".equalsIgnoreCase(type) ? 4 : 2;
        Demande_statut statut = new Demande_statut(java.sql.Date.valueOf(resolvedDate), null, 0, idDemande, statutId);
        demande_statutRepository.save(statut);
        return "redirect:/devis/new";
    }
    
    @GetMapping("/devis/list")
    public String devisList(Model model) {
        model.addAttribute("appTitle", "Forage - Mes devis");
        return "devis-list";
    }
}
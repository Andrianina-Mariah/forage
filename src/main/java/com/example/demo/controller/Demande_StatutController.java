package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Demande;
import com.example.demo.model.Demande_statut;
import com.example.demo.repository.DemandeRepository;
import com.example.demo.repository.Demande_statutRepository;
import com.example.demo.repository.StatutRepository;

@Controller
public class Demande_StatutController {
	private static final DateTimeFormatter INPUT_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

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
    public String doDemandeStatut(@RequestParam("type_demande") int idDemande,
        @RequestParam("type_statut") int idStatut,
        @RequestParam(name = "observation", required = false, defaultValue = "") String observation,
        @RequestParam(name = "date_debut", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateDebut,
        Model model) {
        LocalDateTime resolvedDateDebut = (dateDebut != null) ? dateDebut : LocalDateTime.now();
        demande_statutRepository.closeOpenStatut(idDemande, resolvedDateDebut);
        Date sqlDateDebut = Date.from(resolvedDateDebut.atZone(ZoneId.systemDefault()).toInstant());
        Demande_statut demandeStatut = new Demande_statut(observation, sqlDateDebut, null, 0, idDemande, idStatut);
        demande_statutRepository.save(demandeStatut);
        return "redirect:/demande-statut/new";
    }

    @GetMapping("/demande-statut/list")
    public String demandeStatutList(Model model) {
        model.addAttribute("appTitle", "Forage - Liste des demande statuts");
        model.addAttribute("demandesStatuts", demande_statutRepository.findAll());
        return "demande-statut-list";
    }

    @GetMapping("/demande-statut/edit")
    public String editDemandeStatut(@RequestParam(name = "type_demande", required = false) Integer idDemande, Model model) {
        List<Demande> demandes = demandeRepository.findAll();
        Integer selectedDemandeId = idDemande;
        if (selectedDemandeId == null && !demandes.isEmpty()) {
            selectedDemandeId = demandes.get(0).getId();
        }

        Optional<Demande_statut> demandeStatut = selectedDemandeId == null
            ? Optional.empty()
            : demande_statutRepository.findOpenByDemande(selectedDemandeId);

        model.addAttribute("appTitle", "Forage - Modifier demande statut");
        model.addAttribute("demandes", demandes);
        model.addAttribute("statuts", statutRepository.findAll());
        model.addAttribute("selectedDemandeId", selectedDemandeId);
        model.addAttribute("demandeStatut", demandeStatut.orElse(new Demande_statut()));
        model.addAttribute("dateDebutValue", demandeStatut.map(Demande_StatutController::formatDateTime).orElse(""));
        model.addAttribute("hasOpenStatut", demandeStatut.isPresent());
        return "demande-statut-edit";
    }

    @PostMapping("/demande-statut/update")
    public String updateDemandeStatut(@RequestParam("id") int id,
        @RequestParam("type_demande") int idDemande,
        @RequestParam("type_statut") int idStatut,
        @RequestParam(name = "observation", required = false, defaultValue = "") String observation,
        @RequestParam("date_debut")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateDebut,
        @RequestParam(name = "previous_date_debut", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime previousDateDebut) {

        Date sqlDateDebut = Date.from(dateDebut.atZone(ZoneId.systemDefault()).toInstant());
        Demande_statut demandeStatut = new Demande_statut(observation, sqlDateDebut, null, id, idDemande, idStatut);
        demande_statutRepository.updateOpenStatut(demandeStatut, previousDateDebut);
        return "redirect:/demande-statut/edit?type_demande=" + idDemande;
    }

    private static String formatDateTime(Demande_statut demandeStatut) {
        if (demandeStatut.getDateDebut() == null) {
            return "";
        }
        return INPUT_DATE_TIME.format(
            demandeStatut.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }
}

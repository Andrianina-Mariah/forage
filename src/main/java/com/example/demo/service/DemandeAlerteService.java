package com.example.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DemandeAlerteService {
	private static final LocalTime HEURE_DEBUT_TRAVAIL = LocalTime.of(8, 0);
	private static final LocalTime HEURE_FIN_TRAVAIL = LocalTime.of(16, 0);

	private final JdbcTemplate jdbcTemplate;

	public DemandeAlerteService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DemandeAlerteResponse calculerAlertes(int idDemande) {
		if (!demandeExiste(idDemande)) {
			throw new DemandeIntrouvableException(idDemande);
		}

		List<DemandeStatutPeriode> periodes = trouverPeriodes(idDemande);
		List<DemandeAlerte> alertes = new ArrayList<>();

		for (int i = 0; i < periodes.size(); i++) {
			DemandeStatutPeriode periode = periodes.get(i);
			LocalDateTime fin = periode.dateFin() != null ? periode.dateFin() : LocalDateTime.now();
			long dureeTravailMinute = calculerMinutesTravaillees(periode.dateDebut(), fin);

			jdbcTemplate.update(
				"UPDATE demande_statut SET duree_travail_minute = ? WHERE id = ?",
				dureeTravailMinute,
				periode.id()
			);

			Integer statutSuivant = i + 1 < periodes.size() ? periodes.get(i + 1).typeStatut() : null;
			String libelleStatutSuivant = i + 1 < periodes.size() ? periodes.get(i + 1).libelleStatut() : null;
			Optional<ParametreAlerte> parametre = statutSuivant == null
				? Optional.empty()
				: trouverAlerte(periode.typeStatut(), statutSuivant, dureeTravailMinute);

			alertes.add(new DemandeAlerte(
				periode.id(),
				periode.typeStatut(),
				periode.libelleStatut(),
				statutSuivant,
				libelleStatutSuivant,
				periode.dateDebut(),
				fin,
				dureeTravailMinute,
				parametre.map(ParametreAlerte::alerte).orElse("Aucune"),
				parametre.map(ParametreAlerte::dureeMinute).orElse(null)
			));
		}

		return new DemandeAlerteResponse(idDemande, alertes);
	}

	private boolean demandeExiste(int idDemande) {
		Integer count = jdbcTemplate.queryForObject(
			"SELECT COUNT(*) FROM demande WHERE id = ?",
			Integer.class,
			idDemande
		);
		return count != null && count > 0;
	}

	private List<DemandeStatutPeriode> trouverPeriodes(int idDemande) {
		String sql = """
				SELECT ds.id, ds.type_demande, ds.type_statut, ds.date_debut, ds.date_fin, s.libelle AS libelle_statut
				FROM demande_statut ds
				LEFT JOIN statut s ON s.id = ds.type_statut
				WHERE ds.type_demande = ?
				ORDER BY ds.date_debut ASC, ds.id ASC
				""";
		return jdbcTemplate.query(sql, this::mapPeriode, idDemande);
	}

	private Optional<ParametreAlerte> trouverAlerte(int statutDepart, int statutArrivee, long dureeTravailMinute) {
		String sql = """
				SELECT duree_minute, alerte
				FROM parametre
				WHERE id_statut1 = ?
				  AND id_statut2 = ?
				  AND duree_minute <= ?
				ORDER BY duree_minute DESC
				LIMIT 1
				""";
		List<ParametreAlerte> result = jdbcTemplate.query(
			sql,
			(rs, rowNum) -> new ParametreAlerte(rs.getInt("duree_minute"), rs.getString("alerte")),
			statutDepart,
			statutArrivee,
			dureeTravailMinute
		);
		return result.stream().findFirst();
	}

	private long calculerMinutesTravaillees(LocalDateTime debut, LocalDateTime fin) {
		if (debut == null || fin == null || !fin.isAfter(debut)) {
			return 0;
		}

		long total = 0;
		LocalDate jour = debut.toLocalDate();
		LocalDate dernierJour = fin.toLocalDate();

		while (!jour.isAfter(dernierJour)) {
			LocalDateTime debutJour = LocalDateTime.of(jour, HEURE_DEBUT_TRAVAIL);
			LocalDateTime finJour = LocalDateTime.of(jour, HEURE_FIN_TRAVAIL);
			LocalDateTime debutEffectif = debut.isAfter(debutJour) ? debut : debutJour;
			LocalDateTime finEffectif = fin.isBefore(finJour) ? fin : finJour;

			if (finEffectif.isAfter(debutEffectif)) {
				total += Duration.between(debutEffectif, finEffectif).toMinutes();
			}
			jour = jour.plusDays(1);
		}

		return total;
	}

	private DemandeStatutPeriode mapPeriode(ResultSet rs, int rowNum) throws SQLException {
		return new DemandeStatutPeriode(
			rs.getInt("id"),
			rs.getInt("type_demande"),
			rs.getInt("type_statut"),
			toLocalDateTime(rs.getTimestamp("date_debut")),
			toLocalDateTime(rs.getTimestamp("date_fin")),
			rs.getString("libelle_statut")
		);
	}

	private LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp == null ? null : timestamp.toLocalDateTime();
	}

	private record DemandeStatutPeriode(
		int id,
		int typeDemande,
		int typeStatut,
		LocalDateTime dateDebut,
		LocalDateTime dateFin,
		String libelleStatut
	) {
	}

	private record ParametreAlerte(int dureeMinute, String alerte) {
	}

	public record DemandeAlerteResponse(int idDemande, List<DemandeAlerte> alertes) {
	}

	public record DemandeAlerte(
		int idDemandeStatut,
		int idStatutDepart,
		String statutDepart,
		Integer idStatutArrivee,
		String statutArrivee,
		LocalDateTime dateDebut,
		LocalDateTime dateFin,
		long dureeTravailMinute,
		String alerte,
		Integer seuilMinute
	) {
	}

	public static class DemandeIntrouvableException extends RuntimeException {
		public DemandeIntrouvableException(int idDemande) {
			super("Demande introuvable : " + idDemande);
		}
	}
}

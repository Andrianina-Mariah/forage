package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Demande_statut;

@Repository
public class Demande_statutRepository {
	private final JdbcTemplate jdbcTemplate;

	public Demande_statutRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Demande_statut save(Demande_statut demande_statut) {
		String sql = """
				INSERT INTO demande_statut (type_demande, type_statut, observation, date_debut, date_fin)
				VALUES (?, ?, ?, ?, ?)
				""";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, demande_statut.getTypeDemande());
			statement.setInt(2, demande_statut.getTypeStatut());
			statement.setString(3, demande_statut.getObservation());
			statement.setTimestamp(4, toSqlTimestamp(demande_statut.getDateDebut()));
			statement.setTimestamp(5, toSqlTimestamp(demande_statut.getDateFin()));
			return statement;
		}, keyHolder);

		Number key = keyHolder.getKey();
		if (key != null) {
			demande_statut.setId(key.intValue());
		}
		return demande_statut;
	}

	public Optional<Demande_statut> findById(int id) {
		String sql = """
				SELECT demande_statut.*, statut.libelle as libelle_statut, demande.libelle_demande as libelle_demande
				FROM demande_statut
				LEFT JOIN statut ON demande_statut.type_statut = statut.id
				LEFT JOIN demande ON demande_statut.type_demande = demande.id
				WHERE demande_statut.id = ?
				""";
		List<Demande_statut> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public Optional<Demande_statut> findOpenByDemande(int typeDemande) {
		String sql = """
				SELECT demande_statut.*, statut.libelle as libelle_statut, demande.libelle_demande as libelle_demande
				FROM demande_statut
				LEFT JOIN statut ON demande_statut.type_statut = statut.id
				LEFT JOIN demande ON demande_statut.type_demande = demande.id
				WHERE demande_statut.type_demande = ?
				  AND demande_statut.date_fin IS NULL
				ORDER BY demande_statut.date_debut DESC, demande_statut.id DESC
				LIMIT 1
				""";
		List<Demande_statut> result = jdbcTemplate.query(sql, this::mapRow, typeDemande);
		return result.stream().findFirst();
	}

	public List<Demande_statut> findAll() {
		String sql = """
				SELECT demande_statut.*, statut.libelle as libelle_statut, demande.libelle_demande as libelle_demande
				FROM demande_statut
				LEFT JOIN statut ON demande_statut.type_statut = statut.id
				LEFT JOIN demande ON demande_statut.type_demande = demande.id
				ORDER BY date_debut DESC
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}

	public boolean update(Demande_statut demande_statut) {
		String sql = """
				UPDATE demande_statut
				SET type_demande = ?, type_statut = ?, observation = ?, date_debut = ?, date_fin = ?
				WHERE id = ?
				""";
		int updated = jdbcTemplate.update(sql,
			demande_statut.getTypeDemande(),
			demande_statut.getTypeStatut(),
			demande_statut.getObservation(),
			toSqlTimestamp(demande_statut.getDateDebut()),
			toSqlTimestamp(demande_statut.getDateFin()),
			demande_statut.getId()
		);
		return updated > 0;
	}

	@Transactional
	public boolean updateOpenStatut(Demande_statut demandeStatut, LocalDateTime previousDateDebut) {
		Optional<Demande_statut> current = findById(demandeStatut.getId());
		if (current.isEmpty()) {
			return false;
		}

		Demande_statut oldStatut = current.get();
		boolean updated = update(demandeStatut);
		if (!updated) {
			return false;
		}

		LocalDateTime newDateDebut = toLocalDateTime(demandeStatut.getDateDebut());
		LocalDateTime oldDateDebut = previousDateDebut != null ? previousDateDebut : toLocalDateTime(oldStatut.getDateDebut());
		updatePreviousDateFin(demandeStatut.getTypeDemande(), demandeStatut.getId(), oldDateDebut, newDateDebut);
		return true;
	}

	public boolean deleteById(int id) {
		String sql = "DELETE FROM demande_statut WHERE id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	public int closeOpenStatut(int typeDemande, LocalDateTime dateFin) {
		String sql = "UPDATE demande_statut SET date_fin = ? WHERE type_demande = ? AND date_fin IS NULL";
		return jdbcTemplate.update(sql, Timestamp.valueOf(dateFin), typeDemande);
	}

	private int updatePreviousDateFin(int typeDemande, int currentId, LocalDateTime oldDateDebut, LocalDateTime newDateDebut) {
		if (newDateDebut == null) {
			return 0;
		}

		if (oldDateDebut != null) {
			String exactPreviousSql = """
					UPDATE demande_statut
					SET date_fin = ?
					WHERE type_demande = ?
					  AND id <> ?
					  AND date_fin = ?
					""";
			int updated = jdbcTemplate.update(
				exactPreviousSql,
				Timestamp.valueOf(newDateDebut),
				typeDemande,
				currentId,
				Timestamp.valueOf(oldDateDebut)
			);
			if (updated > 0) {
				return updated;
			}
		}

		String nearestPreviousSql = """
				UPDATE demande_statut
				SET date_fin = ?
				WHERE id = (
					SELECT id FROM (
						SELECT id
						FROM demande_statut
						WHERE type_demande = ?
						  AND id <> ?
						  AND date_debut < ?
						ORDER BY date_debut DESC, id DESC
						LIMIT 1
					) previous_statut
				)
				""";
		return jdbcTemplate.update(
			nearestPreviousSql,
			Timestamp.valueOf(newDateDebut),
			typeDemande,
			currentId,
			Timestamp.valueOf(newDateDebut)
		);
	}

	private Demande_statut mapRow(ResultSet rs, int rowNum) throws SQLException {
		Demande_statut demande_statut = new Demande_statut();
		demande_statut.setId(rs.getInt("id"));
		demande_statut.setTypeDemande(rs.getInt("type_demande"));
		demande_statut.setTypeStatut(rs.getInt("type_statut"));
		demande_statut.setObservation(rs.getString("observation"));
		demande_statut.setTimestampDebut(rs.getTimestamp("date_debut"));
		demande_statut.setTimestampFin(rs.getTimestamp("date_fin"));
		demande_statut.setLibelleStatut(rs.getString("libelle_statut"));
		demande_statut.setLibelleDemande(rs.getString("libelle_demande"));
		return demande_statut;
	}

	private Timestamp toSqlTimestamp(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	private LocalDateTime toLocalDateTime(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime()).toLocalDateTime();
	}

}

package com.example.demo.repository;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Demande;
import com.example.demo.model.Demande_staut;


public class Demande_statutRepository {
	private final JdbcTemplate jdbcTemplate;

	public Demande_statutRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Demande_statut save(Demande_statut demande_statut) {
		String sql = """
				INSERT INTO demande_statut (type_demande, type_statut, date_debut, date_fin)
				VALUES (?, ?, ?, ?)
				""";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, demande_statut.getTypeDemande());
			statement.setString(2, demande_statut.getTypeStatut());
			statement.setDate(3, toSqlDate(demande_statut.getDateDebut()));
			statement.setDate(4, toSqlDate(demande_statut.getDateFin()));
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
				SELECT id, type_demande, type_statut, date_debut, date_fin
				FROM demande_statut
				WHERE id = ?
				""";
		List<Demande_statut> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<Demande_statut> findAll() {
		String sql = """
				SELECT id, type_demande, type_statut, date_debut, date_fin
				FROM demande_statut
				ORDER BY date_debut DESC
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}

	public boolean update(Demande_staut demande_statut) {
		String sql = """
				UPDATE demande_statut
				SET type_demande = ?, type_statut = ?, date_debut = ?, date_fin = ?
				WHERE id = ?
				""";
		int updated = jdbcTemplate.update(sql,
			demande_statut.getLibelleDemande(),
			demande_statut.getIdDemandeur(),
			demande_statut.getReference(),
			demande_statut.getLieu(),
			demande_statut.getIdCommune(),
			toSqlDate(demande_statut.getDateDemande()),
			demande_statut.getId()
		);
		return updated > 0;
	}

	public boolean deleteById(int id) {
		String sql = "DELETE FROM demande_statut WHERE id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	private Demande_statut mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Demande_statut(
			rs.getDate("date_debut"),
			rs.getInt("type_demande"),
			rs.getInt("type_statut"),
			rs.getInt("id"),
			rs.getDate("date_fin")
		);
	}

	private Date toSqlDate(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new Date(date.getTime());
	}

}

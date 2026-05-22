package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Demande_statut;

@Repository
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
			statement.setInt(2, demande_statut.getTypeStatut());
			statement.setTimestamp(3, toSqlTimestamp(demande_statut.getDateDebut()));
			statement.setTimestamp(4, toSqlTimestamp(demande_statut.getDateFin()));
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

	public boolean update(Demande_statut demande_statut) {
		String sql = """
				UPDATE demande_statut
				SET type_demande = ?, type_statut = ?, date_debut = ?, date_fin = ?
				WHERE id = ?
				""";
		int updated = jdbcTemplate.update(sql,
			demande_statut.getTypeDemande(),
			demande_statut.getTypeStatut(),
			toSqlTimestamp(demande_statut.getDateDebut()),
			toSqlTimestamp(demande_statut.getDateFin()),
			demande_statut.getId()
		);
		return updated > 0;
	}

	public boolean deleteById(int id) {
		String sql = "DELETE FROM demande_statut WHERE id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	public int closeOpenStatut(int typeDemande, LocalDateTime dateFin) {
		String sql = "UPDATE demande_statut SET date_fin = ? WHERE type_demande = ? AND date_fin IS NULL";
		return jdbcTemplate.update(sql, Timestamp.valueOf(dateFin), typeDemande);
	}

	private Demande_statut mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		Demande_statut demande_statut = new Demande_statut();
		demande_statut.setId(rs.getInt("id"));
		demande_statut.setTypeDemande(rs.getInt("type_demande"));
		demande_statut.setTypeStatut(rs.getInt("type_statut"));
		demande_statut.setTimestampDebut(rs.getDate("date_debut"));
		demande_statut.setTimestampFin(rs.getDate("date_fin"));
		return demande_statut;
	}

	private Timestamp toSqlTimestamp(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

}

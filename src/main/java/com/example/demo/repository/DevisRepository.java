package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.Date;
import java.util.Optional;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.*;

@Repository
public class DevisRepository {
	private final JdbcTemplate jdbcTemplate;

	public DevisRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Devis save(Devis devis) {
		String sql = """
				INSERT INTO devis (idDemande, type, date, description)
				VALUES (?, ?, ?, ?)
				""";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, devis.getIdDemande());
			statement.setString(2, devis.getType());
			statement.setDate(3, java.sql.Date.valueOf(devis.getDate()));
			statement.setString(4, devis.getDescription());
			return statement;
		}, keyHolder);

		Number key = keyHolder.getKey();
		if (key != null) {
			devis.setId(key.intValue());
		}
		return devis;
	}

	public Optional<Devis> findById(int id) {
		String sql = """
				SELECT id, idDemande, type, date, description
				FROM devis
				WHERE id = ?
				""";
		List<Devis> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<Devis> findAll() {
		String sql = """
				SELECT id, idDemande, type, date, description
				FROM devis
				ORDER BY date DESC
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}

	public boolean update(Devis devis) {
		String sql = """
				UPDATE devis
				SET idDemande = ?, type = ?, date = ?, description = ?
				WHERE id = ?
				""";
		int updated = jdbcTemplate.update(sql,
			devis.getIdDemande(),
			devis.getType(),
			java.sql.Date.valueOf(devis.getDate()),
			devis.getDescription(),
			devis.getId()
		);
		return updated > 0;
	}

	public boolean deleteById(int id) {
		String sql = "DELETE FROM devis WHERE id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	private Devis mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		LocalDate date = rs.getDate("date").toLocalDate();
		return new Devis(
			date,
			rs.getString("description"),
			rs.getInt("id"),
			rs.getInt("idDemande"),
			rs.getString("type")
		);
	}

	private Date toSqlDate(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new Date(date.getTime());
	}
  
}

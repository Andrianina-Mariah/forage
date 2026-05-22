package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Statut;

@Repository
public class StatutRepository {

	private final JdbcTemplate jdbcTemplate;

	public StatutRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Statut save(Statut statut) {
		String sql = """
				INSERT INTO statut (libelle)
				VALUES (?)
				""";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, statut.getLibelle());
			return statement;
		}, keyHolder);

		Number key = keyHolder.getKey();
		if (key != null) {
			statut.setId(key.intValue());
		}
		return statut;
	}

	public Optional<Statut> findById(int id) {
		String sql = """
				SELECT id, libelle
				FROM statut
				WHERE id = ?
				""";
		List<Statut> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<Statut> findAll() {
		String sql = """
				SELECT id, libelle
				FROM statut
				ORDER BY libelle
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}

	public boolean update(Statut statut) {
		String sql = """
				UPDATE statut
				SET libelle = ?
				WHERE id = ?
				""";
		int updated = jdbcTemplate.update(sql,
			statut.getLibelle(),
			statut.getId()
		);
		return updated > 0;
	
	}

	public boolean deleteById(int id) {
		String sql = "DELETE FROM statut WHERE id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	private Statut mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Statut(
			rs.getInt("id"),
			rs.getString("libelle")
		);
	}

}

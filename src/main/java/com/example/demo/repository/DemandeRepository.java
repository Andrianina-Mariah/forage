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

@Repository
public class DemandeRepository {

	private final JdbcTemplate jdbcTemplate;

	public DemandeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Demande save(Demande demande) {
		String sql = """
				INSERT INTO demande (libelle_demande, id_demandeur, reference, lieu_demande, id_commune, date_demande)
				VALUES (?, ?, ?, ?, ?, ?)
				""";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, demande.getLibelleDemande());
			statement.setInt(2, demande.getIdDemandeur());
			statement.setString(3, demande.getReference());
			statement.setString(4, demande.getLieu());
			statement.setInt(5, demande.getIdCommune());
			statement.setDate(6, toSqlDate(demande.getDateDemande()));
			return statement;
		}, keyHolder);

		Number key = keyHolder.getKey();
		if (key != null) {
			demande.setId(key.intValue());
		}
		return demande;
	}

	public Optional<Demande> findById(int id) {
		String sql = """
				SELECT id, libelle_demande, id_demandeur, reference, lieu_demande, id_commune, date_demande
				FROM demande
				WHERE id = ?
				""";
		List<Demande> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<Demande> findAll() {
		String sql = """
				SELECT id, libelle_demande, id_demandeur, reference, lieu_demande, id_commune, date_demande
				FROM demande
				ORDER BY date_demande DESC
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}

	public boolean update(Demande demande) {
		String sql = """
				UPDATE demande
				SET libelle_demande = ?, id_demandeur = ?, reference = ?, lieu_demande = ?, id_commune = ?, date_demande = ?
				WHERE id = ?
				""";
		int updated = jdbcTemplate.update(sql,
			demande.getLibelleDemande(),
			demande.getIdDemandeur(),
			demande.getReference(),
			demande.getLieu(),
			demande.getIdCommune(),
			toSqlDate(demande.getDateDemande()),
			demande.getId()
		);
		return updated > 0;
	}

	public boolean deleteById(int id) {
		String sql = "DELETE FROM demande WHERE id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	private Demande mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Demande(
			rs.getDate("date_demande"),
			rs.getInt("id"),
			rs.getInt("id_commune"),
			rs.getInt("id_demandeur"),
			rs.getString("libelle_demande"),
			rs.getString("lieu_demande"),
			rs.getString("reference")
		);
	}

	private Date toSqlDate(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new Date(date.getTime());
	}
}

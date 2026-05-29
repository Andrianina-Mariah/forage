package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Devis_detail;

@Repository
public class Devis_detailRepository {
	private final JdbcTemplate jdbcTemplate;

	public Devis_detailRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Devis_detail save(Devis_detail devis_detail) {
		String sql = """
				INSERT INTO devis_detail (idDevis, libelle, quantite, prix_unitaire)
				VALUES (?, ?, ?, ?)
				""";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, devis_detail.getIdDevis());
			statement.setString(2, devis_detail.getLibelle());
			statement.setDouble(3, devis_detail.getQuantite());
			statement.setDouble(4, devis_detail.getPrix());
			return statement;
		}, keyHolder);

		Number key = keyHolder.getKey();
		if (key != null) {
			devis_detail.setId(key.intValue());
		}
		return devis_detail;
	}

	public Optional<Devis_detail> findById(int id) {
		String sql = """
				SELECT id, idDevis, libelle, quantite, prix_unitaire
				FROM devis_detail
				WHERE id = ?
				""";
		List<Devis_detail> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<Devis_detail> findByDevisId(int idDevis) {
		String sql = """
			SELECT id, idDevis, libelle, quantite, prix_unitaire
			FROM devis_detail
			WHERE idDevis = ?
			ORDER BY id
			""";

		return jdbcTemplate.query(sql, this::mapRow, idDevis);
	}

	public List<Devis_detail> findAll() {
		String sql = """
				SELECT id, idDevis, libelle, quantite, prix_unitaire
				FROM devis_detail
				ORDER BY idDevis DESC
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}

	public boolean update(Devis_detail devis_detail) {
		String sql = """
				UPDATE devis_detail
				SET idDevis = ?, libelle = ?, quantite = ?, prix_unitaire = ?
				WHERE id = ?
				""";
		int updated = jdbcTemplate.update(sql,
			devis_detail.getIdDevis(),
			devis_detail.getLibelle(),
			devis_detail.getQuantite(),
			devis_detail.getPrix(),
			devis_detail.getId()
		);
		return updated > 0;
	}

	public boolean deleteById(int id) {
		String sql = "DELETE FROM devis_detail WHERE id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	private Devis_detail mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Devis_detail(
			rs.getInt("id"),
			rs.getInt("idDevis"),
			rs.getString("libelle"),
			rs.getDouble("prix_unitaire"),
			rs.getDouble("quantite")
		);
	}

}

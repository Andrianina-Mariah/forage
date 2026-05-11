package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Client;

@Repository
public class ClientRepository {
	private final JdbcTemplate jdbcTemplate;

	public ClientRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<Client> findByEmailAndPassword(String email, String motDePasse) {
		String sql = """
				SELECT id, nom, mail, role, mot_de_passe
				FROM client
				WHERE mail = ? AND mot_de_passe = ?
				""";
		List<Client> result = jdbcTemplate.query(sql, this::mapRow, email, motDePasse);
		return result.stream().findFirst();
	}

	private Client mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Client(
			rs.getInt("id"),
			rs.getString("nom"),
			rs.getString("mail"),
			rs.getString("role"),
			rs.getString("mot_de_passe")
		);
	}
}

package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Commune;


@Repository
public class CommuneRepository {
	private final JdbcTemplate jdbcTemplate;

	public CommuneRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public Optional<Commune> findById(int id) {
		String sql = """
				SELECT id, nom, id_district
				FROM commune
                WHERE id = ?
                """;    
		List<Commune> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<Commune> findAll() {
		String sql = """
				SELECT id, nom, id_district
				FROM commune
				ORDER BY nom
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}
	private Commune mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Commune (
			rs.getInt("id"),
			rs.getString("nom"),
			rs.getInt("id_district")
		);
	}
    
   
}

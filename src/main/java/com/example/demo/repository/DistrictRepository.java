package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.model.District;

public class DistrictRepository {
    private final JdbcTemplate jdbcTemplate;

	public DistrictRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public Optional<District> findById(int id) {
		String sql = """
				SELECT id, libelle, id_region
				FROM district
                WHERE id = ?
                """;    
		List<District> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<District> findAll() {
		String sql = """
				SELECT id, libelle, id_region
				FROM district
				ORDER BY libelle
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}
	private District mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new District (
			rs.getInt("id"),
			rs.getString("libelle"),
			rs.getInt("id_region")
		);
	}
    
}

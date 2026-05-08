package com.example.demo.repository;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.model.Region;

public class RegionRepository {
    private final JdbcTemplate jdbcTemplate;

	public RegionRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public Optional<Region> findById(int id) {
		String sql = """
				SELECT id, nom
				FROM region
                WHERE id = ?
                """;    
		List<Region> result = jdbcTemplate.query(sql, this::mapRow, id);
		return result.stream().findFirst();
	}

	public List<Region> findAll() {
		String sql = """
				SELECT id, nom
				FROM region
				ORDER BY nom
				""";
		return jdbcTemplate.query(sql, this::mapRow);
	}
	private Region mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Region(
			rs.getInt("id"),
			rs.getString("nom")
		);
	}

}

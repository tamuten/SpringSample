package com.example.demo.trySpring;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Map<String, Object> findOne(int id) {

		// sql生成
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	employee_id, ");
		sb.append("	employee_name, ");
		sb.append("	age ");
		sb.append("FROM employee ");
		sb.append("WHERE employee_id=?");
		String query = sb.toString();

		//検索実行
		Map<String, Object> employee = jdbcTemplate.queryForMap(query, id);

		return employee;
	}
}

package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.util.JdbcUtil;

@Repository("UserDaoJdbcImpl4")
public class UserDaoJdbcImpl4 extends UserDaoJdbcImpl {

	@Override
	public List<User> selectMany() throws DataAccessException {
		String sql = JdbcUtil.createSqlString("countUser.sql");
		UserResultSetExtractor extractor = new UserResultSetExtractor();
		return jdbcTemplate.query(sql, extractor);
	}

}

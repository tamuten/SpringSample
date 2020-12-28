package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository("UserDaoJdbcImpl2")
public class UserDaoJdbcImpl2 extends UserDaoJdbcImpl {

	@Override
	public User selectOne(String userId) throws DataAccessException {
		RowMapper<User> rowMapper = new UserRowMapper();
		return jdbcTemplate.queryForObject(SELECT_ONE_BY_USERID, rowMapper, userId);
	}

	@Override
	public List<User> selectMany() throws DataAccessException {
		RowMapper<User> rowMapper = new UserRowMapper();
		return jdbcTemplate.query(SELECT_ALL, rowMapper);
	}

}

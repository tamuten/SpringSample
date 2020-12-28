package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository("UserDaoJdbcImpl3")
public class UserDaoJdbcImpl3 extends UserDaoJdbcImpl {

	@Override
	public User selectOne(String userId) throws DataAccessException {
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		return jdbcTemplate.queryForObject(SELECT_ONE_BY_USERID, rowMapper, userId);
	}

	@Override
	public List<User> selectMany() throws DataAccessException {
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		return jdbcTemplate.query(SELECT_ALL, rowMapper);
	}

}

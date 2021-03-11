package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import com.example.demo.login.util.JdbcUtil;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public int count() throws DataAccessException {
		String sql = JdbcUtil.createSqlString("countUser.sql");
		SqlParameterSource params = new MapSqlParameterSource();

		return namedJdbcTemplate.queryForObject(sql, params, Integer.class);
	}

	@Override
	public int insertOne(User user) throws DataAccessException {
		String sql = JdbcUtil.createSqlString("namedInsertOne.sql");
		SqlParameterSource params = getInsertParam(user);

		return namedJdbcTemplate.update(sql, params);
	}

	private SqlParameterSource getInsertParam(User user) {
		return new MapSqlParameterSource()
				.addValue("userId", user.getUserId())
				.addValue("password", user.getPassword())
				.addValue("userName", user.getUserName())
				.addValue("birthday", user.getBirthday())
				.addValue("age", user.getAge())
				.addValue("marriage", user.isMarriage())
				.addValue("role", user.getRole());
	}

	@Override
	public User selectOne(String userId) throws DataAccessException {
		String sql = JdbcUtil.createSqlString("namedSelectOne.sql");
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", userId);
		RowMapper<User> rowMapper = new UserRowMapper();

		return namedJdbcTemplate.queryForObject(sql, params, rowMapper);
	}

	@Override
	public List<User> selectMany() throws DataAccessException {
		String sql = JdbcUtil.createSqlString("findAll.sql");
		RowMapper<User> rowMapper = new UserRowMapper();

		return namedJdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public int updateOne(User user) throws DataAccessException {
		return namedJdbcTemplate.update(JdbcUtil.createSqlString("namedUpdate.sql"), getUpdateParam(user));
	}

	private SqlParameterSource getUpdateParam(User user) {
		return new MapSqlParameterSource()
				.addValue("userId", user.getUserId())
				.addValue("password", user.getPassword())
				.addValue("userName", user.getUserName())
				.addValue("birthday", user.getBirthday())
				.addValue("age", user.getAge())
				.addValue("marriage", user.isMarriage());
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		String sql = JdbcUtil.createSqlString("namedDelete.sql");
		SqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);

		return namedJdbcTemplate.update(sql, params);
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		String sql = JdbcUtil.createSqlString("findAll.sql");

		UserRowCallbackHandler handler = new UserRowCallbackHandler();

		namedJdbcTemplate.query(sql, handler);

	}

}

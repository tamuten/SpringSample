package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public int count() throws DataAccessException {
		SqlParameterSource params = new MapSqlParameterSource();
		return namedJdbcTemplate.queryForObject(readSqlfile("countUser.sql"), params, Integer.class);
	}

	@Override
	public int insertOne(User user) throws DataAccessException {
		return namedJdbcTemplate.update(readSqlfile("namedInsertOne.sql"), createInsertParameter(user));
	}

	private SqlParameterSource createInsertParameter(User user) {
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
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", userId);
		Map<String, Object> resultMap = namedJdbcTemplate.queryForMap(readSqlfile("namedSelectOne.sql"), params);
		return generateUser(resultMap);
	}

	private User generateUser(Map<String, Object> resultMap) {
		User user = new User();

		user.setUserId((String) resultMap.get("user_id"));
		user.setUserName((String) resultMap.get("user_name"));
		user.setPassword((String) resultMap.get("password"));
		user.setBirthday((Date) resultMap.get("birthday"));
		user.setAge((Integer) resultMap.get("age"));
		user.setMarriage((Boolean) resultMap.get("marriage"));
		user.setRole((String) resultMap.get("role"));

		return user;
	}

	@Override
	public List<User> selectMany() throws DataAccessException {
		SqlParameterSource params = new MapSqlParameterSource();
		List<Map<String, Object>> getList = namedJdbcTemplate.queryForList(readSqlfile("findAll.sql"), params);

		List<User> userList = new ArrayList<>();
		for (Map<String, Object> resultMap : getList) {
			userList.add(generateUser(resultMap));
		}

		return userList;
	}

	@Override
	public int updateOne(User user) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ

	}

	private String readSqlfile(String fileName) {

		StringBuilder sb = new StringBuilder();
		String path = "sql/" + fileName;

		try (InputStream is = new ClassPathResource(path).getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			String sql;
			while ((sql = br.readLine()) != null) {
				sb.append(sql);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return sb.toString();
	}

}

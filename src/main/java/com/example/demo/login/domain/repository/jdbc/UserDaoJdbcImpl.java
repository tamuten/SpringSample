package com.example.demo.login.domain.repository.jdbc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/** 件数取得SQL */
	private static final String COUNT_SQL = "SELECT count(*) FROM m_user";

	/** 全件取得SQL */
	private static final String SELECT_ALL = "SELECT * FROM m_user";

	@Override
	public int count() throws DataAccessException {
		return jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
	}

	@Override
	public int insertOne(User user) throws DataAccessException {
		return jdbcTemplate.update(createInsertOneSqlString(), createInsertOneParam(user));
	}

	/**
	 * Userテーブルに1件InsertするSQL文を生成します。
	 *
	 * @return SQL
	 */
	private String createInsertOneSqlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO m_user(user_id,")
				.append(" password,")
				.append(" user_name,")
				.append(" birthday,")
				.append(" age,")
				.append(" marriage,")
				.append(" role)")
				.append(" VALUES(?,?,?,?,?,?,?)");

		return sb.toString();
	}

	/**
	 * Userテーブルに1件InsertするSQLのパラメータを生成します。
	 *
	 *	@param ユーザーオブジェクト
	 * @return パラメータ
	 */
	private Object[] createInsertOneParam(User user) {
		List<Object> param = new ArrayList<>();

		param.add(user.getUserId());
		param.add(user.getPassword());
		param.add(user.getUserName());
		param.add(user.getBirthday());
		param.add(user.getAge());
		param.add(user.isMarriage());
		param.add(user.getRole());

		return param.toArray();
	}

	@Override
	public User selectOne(String userId) throws DataAccessException {
		return null;
	}

	@Override
	public List<User> selectMany() throws DataAccessException {
		List<Map<String, Object>> getList = jdbcTemplate.queryForList(SELECT_ALL);

		List<User> userList = new ArrayList<>();
		for (Map<String, Object> resultMap : getList) {
			userList.add(createUser(resultMap));
		}

		return userList;
	}

	/**
	 * クエリ取得結果からユーザーを生成
	 *
	 * @param resultMap 取得結果Map
	 * @return ユーザー
	 */
	private User createUser(Map<String, Object> resultMap) {
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
	public int updateOne(User user) throws DataAccessException {
		return 0;
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		return 0;
	}

	@Override
	public void userCsvOut() throws DataAccessException {

	}

}
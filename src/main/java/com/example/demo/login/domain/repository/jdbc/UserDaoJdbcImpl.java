package com.example.demo.login.domain.repository.jdbc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import com.example.demo.login.util.JdbcUtil;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	/** 全件取得SQL */
	protected static final String SELECT_ALL = "SELECT * FROM m_user";

	/** 1件取得SQL */
	protected static final String SELECT_ONE_BY_USERID = "SELECT * FROM m_user WHERE user_id = ?";

	@Override
	public int count() throws DataAccessException {
		String sql = JdbcUtil.createSqlString("countUser.sql");
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public int insertOne(User user) throws DataAccessException {
		String sql = createInsertOneSql();
		Object[] param = createInsertOneParam(user);

		return jdbcTemplate.update(sql, param);
	}

	/**
	 * Userテーブルに1件InsertするSQL文を生成します。
	 *
	 * @return SQL
	 */
	private String createInsertOneSql() {
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
		String password = passwordEncoder.encode(user.getPassword());
		List<Object> param = new ArrayList<>();

		param.add(user.getUserId());
		param.add(password);
		param.add(user.getUserName());
		param.add(user.getBirthday());
		param.add(user.getAge());
		param.add(user.isMarriage());
		param.add(user.getRole());

		return param.toArray();
	}

	@Override
	public User selectOne(String userId) throws DataAccessException {
		Map<String, Object> queryResult = jdbcTemplate.queryForMap(SELECT_ONE_BY_USERID, userId);

		return generateUser(queryResult);
	}

	@Override
	public List<User> selectMany() throws DataAccessException {
		String sql = JdbcUtil.createSqlString("findAll.sql");
		List<Map<String, Object>> getList = jdbcTemplate.queryForList(sql);

		List<User> userList = new ArrayList<>();
		for (Map<String, Object> resultMap : getList) {
			userList.add(generateUser(resultMap));
		}

		return userList;
	}

	/**
	 * クエリ取得結果からユーザーを生成
	 *
	 * @param resultMap 取得結果Map
	 * @return ユーザー
	 */
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
	public int updateOne(User user) throws DataAccessException {
		int rowNumber = jdbcTemplate.update(createUpdateSql(), createUpdateParam(user));
		//		if (rowNumber > 0) {
		//			throw new DataAccessException("トランザクションテスト") {
		//			};
		//		}

		return rowNumber;
	}

	private String createUpdateSql() {
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE m_user")
				.append(" SET")
				.append(" password = ?,")
				.append(" user_name = ?,")
				.append(" birthday = ?,")
				.append(" age = ?,")
				.append(" marriage = ?")
				.append(" WHERE user_id = ?");

		return sb.toString();
	}

	private Object[] createUpdateParam(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		List<Object> param = new ArrayList<>();

		param.add(password);
		param.add(user.getUserName());
		param.add(user.getBirthday());
		param.add(user.getAge());
		param.add(user.isMarriage());
		param.add(user.getUserId());

		return param.toArray();
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		String sql = JdbcUtil.createSqlString("deleteOne.sql");
		return jdbcTemplate.update(sql, userId);
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		String sql = JdbcUtil.createSqlString("findAll.sql");
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		jdbcTemplate.query(sql, handler);
	}

}

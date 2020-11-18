package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface UserDao {

	/**
	 * ユーザーテーブルの件数を取得
	 *
	 * @return 件数
	 * @throws DataAccessException
	 */
	public int count() throws DataAccessException;

	/**
	 * ユーザーテーブルにデータを1件insert.
	 *
	 * @param user ユーザーオブジェクト
	 * @return insert件数
	 * @throws DataAccessException
	 */
	public int insertOne(User user) throws DataAccessException;

	/**
	 * ユーザーテーブルのデータを1件取得
	 *
	 * @param userId ユーザーID
	 * @return ユーザーデータ
	 * @throws DataAccessException
	 */
	public User selectOne(String userId) throws DataAccessException;

	/**
	 * ユーザーテーブルの全データを取得
	 *
	 * @return 全データ
	 * @throws DataAccessException
	 */
	public List<User> selectMany() throws DataAccessException;

	/**
	 * ユーザーテーブルを1件更新
	 *
	 * @param user ユーザーオブジェクト
	 * @return 更新件数
	 * @throws DataAccessException
	 */
	public int updateOne(User user) throws DataAccessException;

	/**
	 * ユーザーテーブルを1件削除
	 *
	 * @param userId ユーザーID
	 * @return 削除件数
	 * @throws DataAccessException
	 */
	public int deleteOne(String userId) throws DataAccessException;

	/**
	 * SQL取得結果をサーバーにCSVで保存する
	 *
	 * @throws DataAccessException
	 */
	public void userCsvOut() throws DataAccessException;
}

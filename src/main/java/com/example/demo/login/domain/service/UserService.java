package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {

	@Autowired
	@Qualifier("UserDaoJdbcImpl4")
	UserDao dao;

	/**
	 * ユーザーを一件登録する。
	 *
	 * @param user ユーザー
	 * @return 登録成功 true / 登録失敗 false
	 */
	public boolean insert(User user) {
		int rowNumber = dao.insertOne(user);
		return rowNumber > 0;
	}

	/**
	 * ユーザーの登録件数を取得
	 *
	 * @return 登録件数
	 */
	public int count() {
		return dao.count();
	}

	/**
	 * ユーザーを全件取得
	 *
	 * @return ユーザーリスト
	 */
	public List<User> selectMany() {
		return dao.selectMany();
	}

	/**
	 * ユーザーIDに紐づくユーザーを取得
	 *
	 * @param userId ユーザーID
	 * @return ユーザー
	 */
	public User selectOne(String userId) {
		return dao.selectOne(userId);
	}

	/**
	 * ユーザーを1件更新
	 *
	 * @param user
	 * @return
	 */
	public boolean updateOne(User user) {
		int rowNumber = dao.updateOne(user);
		return rowNumber > 0;
	}

	public boolean deleteOne(String userId) {
		int rowNumber = dao.deleteOne(userId);
		return rowNumber > 0;
	}
}

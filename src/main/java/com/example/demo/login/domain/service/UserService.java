package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Transactional
@Service
public class UserService {

	@Autowired
	@Qualifier("UserDaoJdbcImpl")
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

	/**
	 * ユーザーを削除
	 *
	 * @param userId
	 * @return
	 */
	public boolean deleteOne(String userId) {
		int rowNumber = dao.deleteOne(userId);
		return rowNumber > 0;
	}

	public void userCsvOut() throws DataAccessException {
		dao.userCsvOut();
	}

	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}
}

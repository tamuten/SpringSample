package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	private Map<String, String> radioMarriage;

	private Map<String, String> initRadioMarriage() {
		Map<String, String> radio = new LinkedHashMap<>();

		radio.put("既婚", "true");
		radio.put("未婚", "false");

		return radio;
	}

	@GetMapping("/home")
	public String getHome(Model model) {
		// コンテンツ部分にホーム画面を表示させるための文字列を登録
		model.addAttribute("contents", "login/home :: home_contents");

		return "login/homeLayout";
	}

	/**
	 * ログアウトする
	 *
	 * @return
	 */
	@PostMapping("/logout")
	public String postLogout() {
		return "redirect:/login";
	}

	/**
	 * ユーザー管理画面を表示
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/userList")
	public String getUserList(Model model) {
		model.addAttribute("contents", "login/userList :: userList_contents");

		model.addAttribute("userList", userService.selectMany());
		model.addAttribute("userListCount", userService.count());

		return "login/homeLayout";
	}

	/**
	 * ユーザー詳細画面を表示
	 *
	 * @param form
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping("/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id") String userId) {
		System.out.println("userId = " + userId);
		model.addAttribute("contents", "login/userDetail :: userDetail_contents");

		radioMarriage = initRadioMarriage();
		model.addAttribute("radioMarriage", radioMarriage);

		if (StringUtils.isNotEmpty(userId)) {
			User user = userService.selectOne(userId);

			BeanUtils.copyProperties(user, form);

			model.addAttribute("signupForm", form);
		}

		return "login/homeLayout";
	}

	/**
	 * ユーザーCSV取得
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model) {

		userService.userCsvOut();

		byte[] bytes = null;

		try {
			bytes = userService.getFile("sample.csv");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	/**
	 * ユーザー更新処理
	 *
	 * @param form
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/userDetail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {
		System.out.println("更新ボタンの処理");
		User user = new User();

		BeanUtils.copyProperties(form, user);

		try {
			boolean result = userService.updateOne(user);
			if (result) {
				model.addAttribute("result", "更新成功");
			} else {
				model.addAttribute("result", "更新失敗");
			}
		} catch (DataAccessException e) {
			model.addAttribute("result", "更新失敗（トランザクションテスト）");
		}

		return getUserList(model);
	}

	/**
	 * ユーザー削除処理
	 *
	 * @param form
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/userDetail", params = "delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {

		System.out.println("削除ボタンの処理");

		boolean result = userService.deleteOne(form.getUserId());
		if (result) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}

		return getUserList(model);
	}

}

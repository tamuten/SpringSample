package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
		// ログイン画面にリダイレクト
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

		if (!StringUtils.isEmpty(userId)) {
			User user = userService.selectOne(userId);

			BeanUtils.copyProperties(user, form);

			model.addAttribute("signupForm", form);
		}

		return "login/homeLayout";
	}

	@GetMapping("/userList/csv")
	public String getUserListCsv(Model model) {
		return getUserList(model);
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

		boolean result = userService.updateOne(user);
		if (result) {
			model.addAttribute("result", "更新成功");
		} else {
			model.addAttribute("result", "更新失敗");
		}

		return getUserList(model);
	}

}

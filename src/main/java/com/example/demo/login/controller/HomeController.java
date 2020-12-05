package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@GetMapping("/home")
	public String getHome(Model model) {
		// コンテンツ部分にホーム画面を表示させるための文字列を登録
		model.addAttribute("contents", "login/home :: home_contents");

		return "login/homeLayout";
	}

	// ログアウト用メソッド
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

	@GetMapping("/userList/csv")
	public String getUserListCsv(Model model) {
		return getUserList(model);
	}

}

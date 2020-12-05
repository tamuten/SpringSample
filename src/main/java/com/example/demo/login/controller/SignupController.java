package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {

	@Autowired
	UserService userService;

	private static final String ROLE_GENERAL = "ROLE_GENERAL";

	// ラジオボタンの実装
	private Map<String, String> radioMarriage;

	/**
	 * ラジオボタンの初期化メソッド
	 *
	 * @return
	 */
	private Map<String, String> initRadioMarriage() {
		Map<String, String> radio = new LinkedHashMap<>();

		// 既婚、未婚をMapに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");

		return radio;

	}

	/**
	 * 登録画面を初期表示する。
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, Model model) {

		//ラジオボタンの初期化メソッド呼び出し
		radioMarriage = initRadioMarriage();

		model.addAttribute("radioMarriage", radioMarriage);

		return "login/signup";
	}

	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated SignupForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {

			return getSignUp(form, model);
		}

		System.out.println(form);

		boolean insertResult = userService.insert(createUserBean(form));

		if (insertResult) {
			System.out.println("insert成功");
		} else {
			System.out.println("Insert失敗");
		}

		// login.htmlにリダイレクト
		return "redirect:/login";
	}

	private User createUserBean(SignupForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setRole(ROLE_GENERAL);

		return user;

	}

}

package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * セキュリティ設定用クラス
 *
 * @author takashi
 *
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private DataSource dataSource;

	private static final String USER_SQL = "SELECT user_id, password, true FROM m_user WHERE user_id = ?";

	private static final String ROLE_SQL = "SELECT user_id, role FROM m_user WHERE user_id = ?";

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 静的リソースを除外
		// 静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers("/webjars/**", "/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 直リンクの禁止
		// ログイン不要ページの設定
		http.authorizeRequests()
				.antMatchers("/webjars/**").permitAll() //webjarsへアクセス許可
				.antMatchers("/css/**").permitAll() // cssへアクセス許可
				.antMatchers("/login").permitAll() // ログインページは直リンクOK
				.antMatchers("/signup").permitAll() // ユーザー登録画面は直リンクOK
				.anyRequest().authenticated(); // それ以外は直リンク禁止

		// ログイン処理
		http.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureUrl("/login")
				.usernameParameter("userId")
				.passwordParameter("password")
				.defaultSuccessUrl("/home", true);

		// ログアウト処理
		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");

		// CSRF対策を無効に設定（一時的）
		//		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// ログイン処理時のユーザー情報をDBから取得する
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(USER_SQL)
				.authoritiesByUsernameQuery(ROLE_SQL)
				.passwordEncoder(passwordEncoder());
	}

}

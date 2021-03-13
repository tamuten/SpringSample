package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * セキュリティ設定用クラス
 *
 * @author takashi
 *
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//	@Autowired
	//	private DataSource dataSource;

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

		// CSRF対策を無効に設定（一時的）
		http.csrf().disable();
	}

	//	@Override
	//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//		// ログイン処理時のユーザー情報をDBから取得する
	//		auth.jdbcAuthentication()
	//				.dataSource(dataSource)
	//				.usersByUsernameQuery(JdbcUtil.createSqlString("findUserIdAndPassword.sql"))
	//				.authoritiesByUsernameQuery(JdbcUtil.createSqlString("getUserRole.sql"));
	//	}

}

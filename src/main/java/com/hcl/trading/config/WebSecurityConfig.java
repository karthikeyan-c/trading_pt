package com.hcl.trading.config;

import com.hcl.trading.service.CustomLogoutHandler;
import com.hcl.trading.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final CustomLogoutHandler customLogoutHandler;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

//	private final MySimpleUrlAuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/sign-up/**", "/sign-in/**")
				.permitAll()
				.anyRequest()
				.authenticated()
//				.permitAll()
				.and()
					.formLogin()
	//				.loginPage("/sign-in")
					.defaultSuccessUrl("/home",true)
	//				.successHandler(successHandler)
					.permitAll()
				.and()
					.logout()
					.addLogoutHandler(customLogoutHandler)
					.permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return authProvider;
	}
}

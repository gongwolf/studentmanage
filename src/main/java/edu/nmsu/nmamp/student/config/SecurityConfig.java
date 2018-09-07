package edu.nmsu.nmamp.student.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import edu.nmsu.nmamp.student.service.UserDetailsServiceImpl;;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @Autowired
	// MyDBAuthenticationService myDBAauthenticationService;
	@Autowired
	DataSource dataSource;

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.userDetailsService(myDBAauthenticationService);
	// }

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
	    encodingFilter.setEncoding("UTF-8");
	    encodingFilter.setForceEncoding(true);
	    http.addFilterBefore(encodingFilter, CsrfFilter.class); 

		// The pages does not require login
		http.authorizeRequests()
		.antMatchers("/", "/login").permitAll()
		.antMatchers("/home*").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/css/**", "/js/**","/scss/**","/vendor/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login")
				.loginProcessingUrl("/login")
				.failureUrl("/login?error")
				.defaultSuccessUrl("/home/homepage")
				.usernameParameter("email")
				.passwordParameter("password")
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.permitAll();
		// http.authorizeRequests().anyRequest().authenticated().and().httpBasic();

		http.sessionManagement().invalidSessionUrl("/login");

	}

	@Bean(name = "passwordEncoder")
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
}
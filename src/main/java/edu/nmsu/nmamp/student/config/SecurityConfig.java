package edu.nmsu.nmamp.student.config;

import edu.nmsu.nmamp.student.authentication.MyDBAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MyDBAuthenticationService myDBAauthenticationService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Users in memory.

		auth.inMemoryAuthentication().withUser("user1").password("12345").roles("USER");
		auth.inMemoryAuthentication().withUser("admin1").password("12345").roles("USER, ADMIN");

		// For User in database.
		auth.userDetailsService(myDBAauthenticationService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();

		// The pages does not require login
//		http.authorizeRequests().antMatchers("/", "/welcome","/login").permitAll();
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();	
		}
}